/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenworldwindsimple;

import com.mycompany.mavenworldwindsimple.solids.Cube;
import gov.nasa.worldwind.Model;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;

/**
 * View class that was taken from the GliderWorldWindow.java file from the 
 * worldwind examples. Since I am not doing anything like adding gliders, it is
 * empty. This may be where the cubes should be added?
 * 
 * @author omnis
 */
public class HelloWorldWindAppTemplateWindow extends WorldWindowGLCanvas {
    
    public void addCube() {
        
        /*
            The code below deviates form the glider example (at least from what 
            understand so far) in that the layer is an abstract layer specific to
            the task (i.e. a Glider object) and that RenderableLayer is not used 
            as a generic way to tack on stuff to WorldWind. I think the goal
            was to maintain a layer "persitently". That is this the cube layer
            instead of here is a new layer that has a cube which is what is 
            happening below.
        */
        RenderableLayer layer = new RenderableLayer();

        Position pos = Position.fromDegrees(32.762999, -117.089078, 5000f);

        Cube cube = new Cube(pos, 10000f);

        layer.addRenderable(cube);
        
        this.getModel().getLayers().add(layer);
    }
}
