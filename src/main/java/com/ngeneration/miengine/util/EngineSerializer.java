package com.ngeneration.miengine.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ngeneration.miengine.Engine;
import com.ngeneration.miengine.graphics.Color;
import com.ngeneration.miengine.math.Point;
import com.ngeneration.miengine.math.Rectangle;
import com.ngeneration.miengine.math.Spline;
import com.ngeneration.miengine.math.Vector2;
import com.ngeneration.miengine.math.Vector3;
import com.ngeneration.miengine.scene.Component;
import com.ngeneration.miengine.scene.GameObject;
import com.ngeneration.miengine.scene.GameObjectRef;
import com.ngeneration.miengine.scene.annotations.Serializable;
import com.ngeneration.miengine.util.indexer.ResourceIndexer;
import com.ngeneration.miengine.util.indexer.ResourceItem;

import lombok.AllArgsConstructor;

public class EngineSerializer {

	private static Map<Integer, JsonObject> scenes = new HashMap<>();
	private static Map<String, String> toShortType = new HashMap<>();
	private static Map<String, String> toLongType = new HashMap<>();

	public static void removeCache(int resourceId) {
		scenes.remove(resourceId);
	}

	private static Map<Class<?>, Class<?>> basicTypes = new HashMap<>();
	static {
		basicTypes.put(String.class, String.class);
		basicTypes.put(Color.class, Color.class);
		basicTypes.put(Point.class, Point.class);
		basicTypes.put(Rectangle.class, Rectangle.class);
		basicTypes.put(Vector2.class, Vector2.class);
		basicTypes.put(Vector3.class, Vector3.class);
	}

	static {
		String basePackage = "com.ngeneration.miengine.scene";
		toShortType.put(basePackage + ".Transform", "t");
		toShortType.put(basePackage + ".SpriteRenderer", "s");
		toShortType.entrySet().forEach(e -> toLongType.put(e.getValue(), e.getKey()));
	}

	public static boolean isBasicType(Class<?> type) {
		return basicTypes.containsKey(type) && type.isAssignableFrom(basicTypes.get(type));
	}

	public static List<Field> getSerializableFields(Class<?> clazz) {
		List<Field> fieldList = new LinkedList<>();
		Stack<Class<?>> hearachy = new Stack<>();
		Class<?> parent = clazz;
		while (parent != null) {
			hearachy.add(parent);
			parent = parent.getSuperclass();
		}
		while (!hearachy.isEmpty()) {
			var cl = hearachy.pop();
			for (var field : cl.getDeclaredFields()) {
				if (Modifier.isTransient(field.getModifiers()) || Modifier.isStatic(field.getModifiers()))
					continue;
				if (Modifier.isPrivate(field.getModifiers())
						&& field.getAnnotationsByType(Serializable.class).length == 0)
					continue;
				field.setAccessible(true);
				fieldList.add(field);
			}
		}
		return fieldList;
	}

	public static String getShortType(String type) {
		return toShortType.getOrDefault(type, type);
	}

	public static String getLongType(String type) {
		if (type.length() == 1)
			type = toLongType.getOrDefault(type, type);
		return type;
	}

	public static String serialize(GameObject object) {
		return new Gson().toJson(serializeToJsonObject(object));
	}

	public static JsonObject serializeToJsonObject(GameObject object) {
		JsonObject json = new JsonObject();
		JsonArray components = new JsonArray();
		json.addProperty("i", object.getId());
		if (!object.isActive())
			json.addProperty("a", object.isActive());
		json.addProperty("tt", object.getTag());
		json.addProperty("n", object.getName());
		json.addProperty("s", object.getState());
		json.addProperty("t",
				serializeBasicType(Vector3.class, object.transform.getLocation()) + ","
						+ serializeBasicType(Vector3.class, object.transform.getScale()) + ","
						+ serializeBasicType(Vector3.class, object.transform.getRotation()));
		if (object instanceof GameObjectRef ref) {
			json.addProperty("r", ref.getResourceId());
		} else {
			if (object.getComponentCount() > 1) {
				var list = object.getComponents();
				list.remove(0); // transformer
				list.forEach(c -> {
					try {
						JsonObject json1 = new JsonObject();
						json1.addProperty("i", c.getId());
						json1.addProperty("t", toShortType.getOrDefault(c.getClass().getCanonicalName(),
								c.getClass().getCanonicalName()));
						json1.addProperty("e", c.isEnabled());
						json1.add("p", serialize(c));
						components.add(json1);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				});
				if (!components.isEmpty())
					json.add("c", components);
			}
			if (!object.getChildren().isEmpty()) {
				JsonArray children = new JsonArray();
				object.getChildren().forEach(c -> children.add(serializeToJsonObject(c)));
				json.add("cc", children);
			}
		}
		return json;
	}

	private static JsonObject serialize(Object component) throws IllegalArgumentException, IllegalAccessException {
		JsonObject properties = new JsonObject();
		for (Field field : getSerializableFields(component.getClass())) {
			field.setAccessible(true);
			var type = field.getType();
			Object value = field.get(component);
			if (value == null)
				continue;

			if (type.isPrimitive()) {
				if (value instanceof Boolean v) {
					if (!v)
						properties.addProperty(field.getName(), v);
				} else if (value instanceof Integer v)
					properties.addProperty(field.getName(), v);
				else if (value instanceof Float v)
					properties.addProperty(field.getName(), v);
				else if (value instanceof Double v)
					properties.addProperty(field.getName(), v);
				else
					throw new RuntimeException("invalid primitive: " + value);
			} else if (isBasicType(type)) {
				properties.addProperty(field.getName(), serializeBasicType(type, value));
			} else if (type == Spline.class) {
				StringBuilder builder = new StringBuilder();
				((Spline) value).getPoints().forEach(p -> {
					if (!builder.isEmpty())
						builder.append(",");
					builder.append(serializeBasicType(Vector2.class, p.position)).append(",")
							.append(serializeBasicType(Vector2.class, p.handler1)).append(",")
							.append(serializeBasicType(Vector2.class, p.handler2)).append(",").append(p.mode);
				});
				properties.addProperty(field.getName(), builder.toString());
			} else if (ResourceItem.class.isAssignableFrom(type)) {
				var resourceItem = (ResourceItem) value;
				properties.addProperty(field.getName(),
						resourceItem.getResource().getId() + "," + resourceItem.getId());
			} else if (value instanceof GameObject || value instanceof Component) {
				properties.addProperty(field.getName(),
						value instanceof GameObject ? ((GameObject) value).getId() : ((Component) value).getId());
			} else if (value instanceof Collection list) {
				if (!list.isEmpty()) {
					JsonArray array = new JsonArray();
					for (var item : list)
						if (item != null) {
							var ser = serialize(item);
							ser.addProperty("$", getShortType(item.getClass().getCanonicalName()));
							array.add(ser);
						}
					properties.add(field.getName(), array);
				}
			} else {
				properties.add(field.getName(), serialize(value));
			}
		}
		return properties;
	}

	public static GameObject deserialize(File file, ResourceIndexer indexer, ClassLoader cl)
			throws FileNotFoundException, IOException {
		return deserialize(new FileInputStream(file), indexer, cl);
	}

	public static GameObject deserialize(int id, ResourceIndexer indexer, ClassLoader cl) {
		String path = null;
		try {
			var json = scenes.get(id);
			if (json == null) {
				var template = indexer.getTemplate(id);
				if (template == null)
					return null;
				path = template.getPath();
				try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(path))) {
					Gson gson = new Gson();
					json = gson.fromJson(new BufferedReader(new InputStreamReader(stream)), JsonObject.class);
					scenes.put(id, json);
				}
			}
			return EngineSerializer.deserialize(id, json, indexer, cl);
		} catch (Exception e) {
			throw new RuntimeException("Exception while load scene: " + path, e);
		}
	}

	public static GameObject deserialize(InputStream inputStream, ResourceIndexer indexer, ClassLoader cl)
			throws FileNotFoundException, IOException {
		try (BufferedInputStream stream = new BufferedInputStream(inputStream)) {
			Gson gson = new Gson();
			return deserialize(0, gson.fromJson(new BufferedReader(new InputStreamReader(stream)), JsonObject.class),
					indexer, cl);
		}
	}

	public static GameObject deserialize(int resourceId, JsonObject json, ResourceIndexer indexer, ClassLoader cl) {
		List<ReferenceData> referenced = new LinkedList<>();
		List<ReferenceData> references = new LinkedList<>();
		Map<Object, Map<String, ResourceItem>> resources = new HashMap<>();
		var root = deserialize(resourceId, json, indexer, referenced, references, resources, cl);
		var map = referenced.stream().collect(Collectors.toMap(r -> r.id, r -> r.instance));
		references.forEach(r -> {
			try {
				r.field.setAccessible(true);
				r.field.set(r.instance, map.get(r.id));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
//		resources.entrySet().forEach(entry -> entry.getValue().entrySet()
//				.forEach(e -> e.getValue().registerReference((Component) entry.getKey(), e.getKey(), e.getValue())));
		return root;
	}

	private static GameObject deserialize(int resourceId, JsonObject json, ResourceIndexer indexer,
			List<ReferenceData> referenced, List<ReferenceData> references,
			Map<Object, Map<String, ResourceItem>> resources, ClassLoader cl) {
		GameObject object = resourceId > 0 && Engine.env != Engine.ENV.PROD ? new GameObjectRef(resourceId)
				: new GameObject();
		setCommonData(object, json);
		if (json.get("i") instanceof JsonElement v)
			referenced.add(new ReferenceData(object, null, v.getAsInt()));
		var c = json.get("c");
		if (c != null)
			c.getAsJsonArray().forEach(e -> {
				try {
					JsonObject component = e.getAsJsonObject();
					var type = getLongType(component.get("t").getAsString());
					Component instance = (Component) (cl != null && type.startsWith("game.") ? cl.loadClass(type)
							: Class.forName(type)).newInstance();
					var ex = component.get("e");
					instance.setEnabled(ex == null ? true : ex.getAsBoolean());
					if (component.get("i") instanceof JsonElement v)
						referenced.add(new ReferenceData(instance, null, v.getAsInt()));
					var properties = component.get("p");
					if (properties != null)
						deserializeObject(object, instance, properties.getAsJsonObject(), indexer, references,
								resources);
					object.addComponent(instance);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
		// children
		var e = json.get("cc");
		if (e != null)
			e.getAsJsonArray().forEach(ee -> {
				var data = ee.getAsJsonObject();
				GameObject child = null;
				if (data.get("r") != null) {
					child = deserialize(data.get("r").getAsInt(), indexer, cl);
					if (child == null) {
						if (Engine.env == Engine.ENV.EDITOR)
							child = new GameObjectRef(-1);
						else
							throw new RuntimeException("Child Scene not found: " + data.get("r").getAsInt());
					}

					child.transform.addToLocation(child.transform.getLocation().mul(-1));

					object.addChild(child);

					// override props
//					setCommonData(child, data);

					child.setName(data.get("n").getAsString());
					float[] floats = toFloats(data.get("t").getAsString().split(","));
					var locP = new Vector3(floats[0], floats[1], floats[2]);
					locP.sub(object.transform.getLocation());
					var locS = new Vector3(floats[3], floats[4], floats[5]);
					locS.scl(object.transform.getScale().inv());
					var locR = new Vector3(floats[6], floats[7], floats[8]);
					locR.sub(object.transform.getRotation());
					child.transform.setLocalLocation(locP);
					child.transform.setLocalRotation(locR);
					child.transform.setLocalScale(locS);

					if (data.get("i") instanceof JsonElement v)
						referenced.add(new ReferenceData(object, null, v.getAsInt()));
				} else {
					child = deserialize(0, ee.getAsJsonObject(), indexer, referenced, references, resources, cl);
					object.addChild(child);

				}
			});
		return object;
	}

	private static void setCommonData(GameObject object, JsonObject json) {
		var value = json.get("s");
		if (value != null)
			object.setState(value.getAsInt());
		object.setName(json.get("n").getAsString());
		value = json.get("a");
		if (value != null)
			object.setActive(value.getAsBoolean());
		value = json.get("tt");
		if (value != null)
			object.setTag(value.getAsString());
		float[] floats = toFloats(json.get("t").getAsString().split(","));
		object.transform.setLocation(floats[0], floats[1], floats[2]);
		object.transform.setScale(floats[3], floats[4], floats[5]);
		object.transform.setRotation(floats[6], floats[7], floats[8]);
	}

	private static float[] toFloats(String[] split) {
		float[] floats = new float[split.length];
		for (int i = 0; i < split.length; i++)
			floats[i] = Float.parseFloat(split[i]);
		return floats;
	}

	@AllArgsConstructor
	private static class ReferenceData {
		private Object instance;
		private Field field;
		private int id;
	}

	private static void deserializeObject(GameObject object, Object instance, JsonObject json, ResourceIndexer indexer,
			List<ReferenceData> references, Map<Object, Map<String, ResourceItem>> resources) {
		json.entrySet().forEach(property -> {
			Field field;
			try {
				try {
					field = instance.getClass().getField(property.getKey());
					field.setAccessible(true);
				} catch (NoSuchFieldException e) {
					field = getSerializableFields(instance.getClass()).stream()
							.filter(f -> f.getName().equals(property.getKey())).findAny().orElse(null);
				}
				if (field == null) {
					System.out.println("field not found: " + property.getKey());
					return;
				}
				var type = field.getType();
				Object value = field.get(instance);
				field.setAccessible(true);
				if (field.getType().isPrimitive()) {
					field.set(instance, getPrimitive(type, property.getValue().getAsString()));
				} else if (isBasicType(type)) {
					if (type == String.class) {
						field.set(instance, property.getValue().getAsString());
					} else {
						String[] values = property.getValue().getAsString().split(",");
						if (value == null)
							field.set(instance, value = type.newInstance());
						int[] vv = new int[1];
						for (var ff : type.getDeclaredFields()) {
							if (Modifier.isTransient(ff.getModifiers()) || Modifier.isStatic(ff.getModifiers()))
								continue;
							ff.setAccessible(true);
							ff.set(value, getPrimitive(ff.getType(), values[vv[0]++]));
						}
					}
				} else if (type == Spline.class) {
					Spline spline = new Spline();
					var dd = property.getValue().getAsString().split(",");
					if (dd.length > 1)
						spline.addPoint(dd);
					field.set(instance, spline);
				} else if (ResourceItem.class.isAssignableFrom(type) && object != null) {
					var values = property.getValue().getAsString().split(",");
					var resource = indexer.getTemplate(Integer.parseInt(values[0]));
					var resourceItem = resource == null ? null : resource.getResource(Integer.parseInt(values[1]));
					if (resourceItem != null) {
						field.set(instance, resourceItem);
						resources.computeIfAbsent(instance, r -> new HashMap<String, ResourceItem>())
								.put(field.getName(), resourceItem);
					}
				} else {
					if (type == GameObject.class || Component.class.isAssignableFrom(type)) {
						references.add(new ReferenceData(instance, field, property.getValue().getAsInt()));
					} else if (Collection.class.isAssignableFrom(type)) {
						if (value instanceof Collection c) {
							var v = property.getValue();
							if (v != null) {
								for (var arrayItem : v.getAsJsonArray()) {
									var itemJson = arrayItem.getAsJsonObject();
									var listType = getLongType(itemJson.get("$").getAsString());
									var itemInstance = Class.forName(listType).newInstance();
									deserializeObject(null, itemInstance, itemJson, indexer, references, resources);
									c.add(itemInstance);
								}
							}
						}
					} else {
						var pinstance = type.newInstance();
						deserializeObject(null, pinstance, property.getValue().getAsJsonObject(), indexer, references,
								resources);
						field.set(instance, pinstance);
					}
				}
			} catch (SecurityException | IllegalArgumentException | IllegalAccessException
					| InstantiationException e1) {
				if (e1 instanceof NoSuchFieldException)
					System.out.println("Field not found: " + e1.getMessage());
				else
					e1.printStackTrace();
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public static Object getPrimitive(Class<?> type, String value) {
		if (type.getName().equals("float"))
			return value.isEmpty() || value.equals(".") || value.equals("-") || value.equals("-.") ? 0
					: Float.parseFloat(value);
		else if (type.getName().equals("int"))
			return value.isEmpty() || value.equals("-") ? 0 : Integer.parseInt(value);
		else if (type.getName().equals("double"))
			return value.isEmpty() || value.equals(".") ? 0 : Double.parseDouble(value);
		else if (type.getName().equals("boolean"))
			return value.isEmpty() ? false : Boolean.parseBoolean(value);
		else if (type.getName().equals("long"))
			return value.isEmpty() ? 0 : Long.parseLong(value);
		else if (type == String.class)
			return value;
		else
			throw new RuntimeException("invalid primitive type: " + type);
	}

	public static String serializeBasicType(Object value) {
		return serializeBasicType(value.getClass(), value);
	}

	private static String serializeBasicType(Class<?> type, Object value) {
		if (type == String.class)
			return value.toString();
		String val = "";
		for (var ff : type.getDeclaredFields()) {
			if (Modifier.isTransient(ff.getModifiers()) || Modifier.isStatic(ff.getModifiers()))
				continue;
			ff.setAccessible(true);
			try {
				val += val.length() > 0 ? "," + ff.get(value) : String.valueOf(ff.get(value));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				return null;
			}
		}
		return val;
	}

}
