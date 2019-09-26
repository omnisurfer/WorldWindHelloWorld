/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenworldwindsimple.solids;

import gov.nasa.worldwind.geom.Matrix;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.pick.PickSupport;
import gov.nasa.worldwind.render.DrawContext;
import gov.nasa.worldwind.render.Renderable;
import java.awt.Color;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import com.mycompany.mavenworldwindsimple.solids.OrderedCube;
/**
 *
 * @author omnis
 */
/*
 cube reference example code: https://github.com/gaeaplus/gaeaplus/blob/master/src/gov/nasa/worldwindx/examples/tutorial/Cube.java
 Note the code in this hello world is not a direct copy of the above code so. I was following the tutorial here: 
 https://worldwind.arc.nasa.gov/java/tutorials/build-a-custom-renderable/ which does not present the full source so I ended up guessing 
 where things would be put until I found the full reference source.

 Picking info: https://forum.worldwindcentral.com/forum/world-wind-java-forums/development-help/10391-solved-picking-selecting-intesecting-or-whatever-it-is-called

*/
public class Cube implements Renderable{
    
    protected Position position;
    protected double size;
    
    protected PickSupport pickSupport = new PickSupport();
    
    public Cube(Position position, double sizeInMeters) {
        this.position = position;
        this.size = sizeInMeters;
    }
    
    @Override
    public void render(DrawContext dc) {
                
        //get the gl interface that is held within the worldwind drawContext
        GL2 gl = dc.getGL().getGL2();            
            
        //set up drawing state
        this.beginDrawing(dc);

        try {                                    

            if(dc.isPickingMode()) {
                Color pickColor = dc.getUniquePickColor();
                this.pickSupport.addPickableObject(pickColor.getRGB(), this, this.position);
                gl.glColor3ub((byte)pickColor.getRed(), (byte)pickColor.getGreen(), (byte)pickColor.getBlue());
            }
            //transform cube to globe basis vectors
            gl.glMatrixMode(GL2.GL_MODELVIEW);

            Matrix matrix = dc.getGlobe().computeSurfaceOrientationAtPosition(this.position);
            matrix = dc.getView().getModelviewMatrix().multiply(matrix);

            //look into matrix stack: https://www.gamedev.net/forums/topic/501591-glscaled/
            double[] matrixArray = new double[16];
            matrix.toArray(matrixArray, 0, false);
            gl.glLoadMatrixd(matrixArray, 0);
            
            //draw the cube
            gl.glScaled(this.size, this.size, this.size);            
            this.drawUnitCube(dc);
        
        }
        finally {
            this.endDrawing(dc);
        }                        
    }
    
    protected void beginDrawing(DrawContext dc) {
        GL2 gl = dc.getGL().getGL2();
        
        int attrMask = GL2.GL_CURRENT_BIT | GL.GL_COLOR_BUFFER_BIT;
        
        gl.glPushAttrib(attrMask);
        
        if(!dc.isPickingMode()) {
            dc.beginStandardLighting();        
        }
    }
    
    protected void endDrawing(DrawContext dc) {
        GL2 gl = dc.getGL().getGL2();
        
        if(!dc.isPickingMode()) {
            dc.endStandardLighting();        
        }
        
        gl.glPopAttrib();
    }
    
    protected void drawUnitCube(DrawContext dc) {
        // Vertices of a unit cube, centered on the origin.
        float[][] v = {
            {-0.5f, 0.5f, -0.5f}, {-0.5f, 0.5f, 0.5f}, 
            {0.5f, 0.5f, 0.5f}, {0.5f, 0.5f, -0.5f}, 
            {-0.5f, -0.5f, 0.5f}, {0.5f, -0.5f, 0.5f}, 
            {0.5f, -0.5f, -0.5f}, {-0.5f, -0.5f, -0.5f}};

        // Array to group vertices into faces
        /*
        
          7___ 4
         /|  /|
        0---1 |
        | 6 | 5
        |/  |/
        3---2
        
        */
        int[][] faces = {{0, 1, 2, 3}, {2, 5, 6, 3}, {1, 4, 5, 2}, {0, 7, 4, 1}, {0, 7, 6, 3}, {4, 7, 6, 5}};

        // Normal vectors for each face
        float[][] n = {{0, 1, 0}, {1, 0, 0}, {0, 0, 1}, {-1, 0, 0}, {0, 0, -1}, {0, -1, 0}};
        
        GL2 gl = dc.getGL().getGL2();
        
        //note the demo advises against using immediate mode and instead to use buffers
        gl.glBegin(GL2.GL_QUADS);
        
        try {
            for(int i = 0; i < faces.length; i++) {
                gl.glNormal3f(n[i][0], n[i][1], n[i][2]);
                
                for(int j = 0; j < faces[0].length; j++) {
                    gl.glVertex3f(
                            v[faces[i][j]][0], 
                            v[faces[i][j]][1], 
                            v[faces[i][j]][2]
                    );
                }
            }
        }
        finally {
            gl.glEnd();
        }
    }
}
