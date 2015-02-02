package ru.camino.tinyrenderer.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ObjModel {
    public static final String TAG = ObjModel.class.getSimpleName();

    public List<Vertex> vertices;
    public List<Face> faces;

    public ObjModel(Context c, String objFileName) throws IOException {
        vertices = new ArrayList<>();
        faces = new ArrayList<>();

        final InputStream is = c.getAssets().open(objFileName);
        final BufferedReader br = new BufferedReader(new InputStreamReader(is));

        int linesCount = 0;

        String line;

        while (true) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            linesCount++;

            if (line.startsWith(Vertex.PREFIX)) {
                vertices.add(new Vertex(line));
            } else if (line.startsWith(Face.PREFIX)) {
                faces.add(new Face(line));
            }
        }

        Log.d(TAG, "File '" + objFileName + "' loaded. Lines: " + linesCount + ", vertices: " + vertices.size() + ", faces: " + faces.size());
    }

    public class Vertex {
        public static final String PREFIX = "v ";

		private static final String TO_STRING_PATTERN = "x=%f, y=%f, z=%f";

        public float x;
        public float y;
        public float z;

        public Vertex(String s) {
            if (s.startsWith(PREFIX)) {
                final String[] ps = s.split(" +");
                x = Float.parseFloat(ps[1]);
                y = Float.parseFloat(ps[2]);
                z = Float.parseFloat(ps[3]);
            } else {
                throw new IllegalArgumentException("Wrong line prefix for vertice at '" + s + "'");
            }
        }

		@Override
		public String toString() {
			return String.format(TO_STRING_PATTERN, x, y, z);
		}
	}

    public class Face {
        public static final String PREFIX = "f ";

        public int[] vertices;
        public int[] textures;
        public int[] normals;

        public Face(String s) {
            vertices = new int[3];
            textures = new int[3];
            normals = new int[3];

            if (s.startsWith(PREFIX)) {
                final String[] ps = s.split(" +");

                final String[] xs = ps[1].split("/");
                final String[] ys = ps[2].split("/");
                final String[] zs = ps[3].split("/");

                vertices[0] = Integer.parseInt(xs[0]) - 1;
                vertices[1] = Integer.parseInt(ys[0]) - 1;
                vertices[2] = Integer.parseInt(zs[0]) - 1;

                textures[0] = Integer.parseInt(xs[1]) - 1;
                textures[1] = Integer.parseInt(ys[1]) - 1;
                textures[2] = Integer.parseInt(zs[1]) - 1;

                normals[0] = Integer.parseInt(xs[2]) - 1;
                normals[1] = Integer.parseInt(ys[2]) - 1;
                normals[2] = Integer.parseInt(zs[2]) - 1;
            } else {
                throw new IllegalArgumentException("Wrong line prefix for face at '" + s + "'");
            }
        }
    }
}
