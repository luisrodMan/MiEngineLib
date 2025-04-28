package com.ngeneration.miengine.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ngeneration.miengine.graphics.Camera;

import lombok.Getter;
import lombok.Setter;

public class GameObject {

	private static final int STARTED = 1;
	private static final int ACTIVE = 2;
	private static final int SELECTION_ROOT = 4;
	private static final int SELECTION_BLOCKED = 8;

	private static Camera camera;
	private static int _id = 1;

	static final Map<GameObject, List<GameObject>> toDispatch = new HashMap<>();
	static final List<GameObject> toDestroy = new ArrayList<>();

	static int generateId() {
		return _id++;
	}

	public static void setRootCamera(Camera camera) {
		GameObject.camera = camera;
	}

	public static Camera getRootCamera() {
		return camera;
	}

	public final Transform transform = new Transform();

	@Getter
	private GameObject parentGameObject;
	@Getter
	@Setter
	private int id = GameObject.generateId();
	@Getter
	@Setter
	private String name;
	private final List<Component> components = new ArrayList<>();
	final List<GameObject> children = new ArrayList<>();
	private int state = ACTIVE;
	private String tag;

	public GameObject() {
		this("GameObject");
	}

	public GameObject(String name) {
		transform.gameObject = this;
		this.name = name;
	}

	public int getState() {
		return state;
	}

	private void addStatus(int status) {
		this.state |= status;
	}

	private boolean hasStatus(int status) {
		return (this.state & status) > 0;
	}

	private void removeStatus(int status) {
		this.state &= ~status;
	}

	public void setActive(boolean value) {
		if (value)
			addStatus(ACTIVE);
		else
			removeStatus(ACTIVE);
	}

	public boolean isActive() {
		return hasStatus(ACTIVE);
	}

	public void addComponent(Component component) {
		addComponent(components.size() + 1, component);
	}

	public void removeComponent(Component component) {
		components.remove(component);
	}

	public void removeAllComponents() {
		components.clear();
	}

	public void removeAllChildren() {
		getChildren().forEach(this::remove);
	}

	public void addComponent(int index, Component component) {
		index--;// first is transform
		if (components.contains(component))
			throw new RuntimeException(
					"Component already added - component: " + component + " in object: " + getName());
		components.add(index, component);
		component.gameObject = this;
		component.transform = transform;
		if (hasStatus(STARTED)) {
			component.start();
		}
	}

	public <T extends Component> List<T> getComponents(Class<T> type) {
		List<T> list = (List<T>) components.stream().filter(c -> type.isAssignableFrom(c.getClass())).toList();
		if (type == Transform.class) {
			list.add(0, type.cast(transform));
		}
		return list;
	}

	public <T extends Component> T getComponent(Class<T> type) {
		return type.cast(type == Transform.class ? transform
				: components.stream().filter(c -> type.isAssignableFrom(c.getClass())).findAny().orElse(null));
	}

	public List<Component> getComponents() {
		var components = new ArrayList<Component>(this.components.size() + 1);
		components.add(transform);
		components.addAll(this.components);
		return components;
	}

	public int getComponentCount() {
		return components.size() + 1;
	}

	public GameObject getChild(int idx) {
		return children.get(idx);
	}

	public void addChild(GameObject child) {
		boolean started = hasStatus(STARTED);
		if (child.parentGameObject != null)
			throw new RuntimeException("Already has a parent");
		child.parentGameObject = this;
		children.add(child);
		child.onAttached();
		if (started && !child.hasStatus(STARTED))
			child.start();
	}

	public void addChild(int targetRow, GameObject object) {
		addChildren(targetRow, List.of(object));
	}

	public void addChildren(int targetRow, List<GameObject> list) {
		boolean started = hasStatus(STARTED);
		for (GameObject child : list) {
			if (child.parentGameObject != null)
				throw new RuntimeException("Already has a parent");
			child.parentGameObject = this;
			if (started && !child.hasStatus(STARTED))
				child.start();
		}
		children.addAll(targetRow, list);
	}

	public void remove(int idx) {
		remove(children.get(idx));
	}

	public void remove(GameObject c) {
		if (c.parentGameObject != this)
			throw new RuntimeException("Not in parent");
		children.remove(c);
		c.parentGameObject = null;
		c.removeStatusRecursive(STARTED);
	}

	private void removeStatusRecursive(int status) {
		removeStatus(status);
		if (hasChildren())
			children.stream().forEach(c -> c.removeStatusRecursive(status));
	}

	public List<GameObject> getChildren() {
		return new ArrayList<>(children);
	}

	public void onAttached() {
		transform.gameObject = this;
		for (var component : components) {
			component.gameObject = this;
			component.transform = transform;
			component.onAttached();
		}
		for (var child : children) {
			child.onAttached();
		}
	}

	public void start() {
		addStatus(STARTED);
		transform.gameObject = this;
		for (var component : components) {
			component.gameObject = this;
			component.transform = transform;
			component.start();
		}
		for (var child : children) {
			child.start();
		}
	}

	public void update(float delta) {
		if (!isActive())
			return;

		for (var component : components) {
			if (!component.isEnabled()) {
				continue;
			}
			component.gameObject = this;
			component.transform = transform;
			component.update(delta);
		}
		for (var child : children) {
			child.update(delta);
		}
	}

	public void updatePhysics(float delta) {
		if (!isActive())
			return;
		for (var component : components) {
			if (!component.isEnabled())
				continue;
			component.gameObject = this;
			component.transform = transform;
			component.updatePhysics(delta);
		}
		for (var child : children) {
			child.updatePhysics(delta);
		}
	}

	public void render() {
		if (!isActive())
			return;

		// me
		for (var component : components) {
			component.gameObject = this;
			component.transform = transform;
			component.render();
		}

		for (var child : children) {
			// pree
			child.render();

			// post
		}

	}

	public boolean hasChildren() {
		return children != null && !children.isEmpty();
	}

	public static void dispatch(GameObject parent, GameObject child) {
		toDispatch.computeIfAbsent(parent, k -> new ArrayList<GameObject>(1)).add(child);
	}

	public static void destroy(GameObject gameObject) {
		toDestroy.add(gameObject);
	}

	public void addComponents(List<Component> components2) {
		components2.forEach(this::addComponent);
	}

	public void addChildren(List<GameObject> components2) {
		components2.forEach(this::addChild);
	}

	public GameObject getByName(String name) {
		for (var c : children)
			if (name == c.getName() || (name != null && name.equals(c.getName())))
				return c;
		return null;
	}

	public String getTag() {
		return tag;
	}

	public boolean compareTag(String tag) {
		return tag == this.tag || (this.tag != null && this.tag.equals(tag));
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public boolean isSelectionRoot() {
		return hasStatus(SELECTION_ROOT);
	}

	public boolean isSelectionBlocked() {
		return hasStatus(SELECTION_BLOCKED);
	}

	public void setAsSelectionRoot(boolean value) {
		if (value)
			addStatus(SELECTION_ROOT);
		else
			removeStatus(SELECTION_ROOT);
	}

	public void setSelectionBlocked(boolean value) {
		if (value)
			addStatus(SELECTION_BLOCKED);
		else
			removeStatus(SELECTION_BLOCKED);
	}

	public static GameObject findGameObjectWithTag(String tag) {
		var parent = SceneManager.getActiveScene();
		if (parent.compareTag(tag))
			return parent;
		return findGameObjectByTag(parent, tag);
	}

	public static GameObject findGameObjectByTag(GameObject parent, String tag) {
		for (var c : parent.children) {
			if (c.compareTag(tag))
				return c;
		}
		for (var c : parent.children) {
			var f = findGameObjectByTag(c, tag);
			if (f != null)
				return f;
		}
		return null;
	}

	public void setState(int value) {
		this.state = value;
	}

}
