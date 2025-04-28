package com.ngeneration.miengine.math;

import java.util.ArrayList;
import java.util.List;

import com.ngeneration.miengine.scene.annotations.Tool;

@Tool(label = "Edit", tool = "com.nxtr.spengine.views.scene.SplineTool")
public class Spline {

	private List<SplinePoint> points = new ArrayList<>();

	public void addPoint(int i, Vector2 point, Vector2 handler, int mode) {
		SplinePoint spoint = new SplinePoint();
		spoint.position.set(point);
		spoint.handler1.set(handler);
		spoint.handler2.set(-handler.x, handler.y);
		points.add(i, spoint);
	}

	public void addPoint(String[] values) {
		for (int i = 0; i < values.length; i += 7) {
			SplinePoint spoint = new SplinePoint();
			spoint.position.set(Float.parseFloat(values[i]), Float.parseFloat(values[i + 1]));
			spoint.handler1.set(Float.parseFloat(values[i + 2]), Float.parseFloat(values[i + 3]));
			spoint.handler2.set(Float.parseFloat(values[i + 4]), Float.parseFloat(values[i + 5]));
			spoint.mode = Integer.parseInt(values[i + 6]);
			points.add(spoint);
		}
	}

	public void addPoint(Vector2 point, Vector2 handler, int mode) {
		addPoint(points.size(), point, handler, mode);
	}

	public List<SplinePoint> getPoints() {
		return points;
	}

	public static class SplinePoint {

		public static final int SINGLE = 1;
		public static final int PARALLEL = 2;

		public int mode = 1;
		public Vector2 position = new Vector2();
		public Vector2 handler1 = new Vector2();
		public Vector2 handler2 = new Vector2();

	}

	public int getPointCount() {
		return points.size();
	}

}
