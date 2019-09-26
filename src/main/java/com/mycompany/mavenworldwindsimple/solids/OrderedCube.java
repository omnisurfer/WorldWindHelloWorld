/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenworldwindsimple.solids;

import gov.nasa.worldwind.geom.Extent;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Vec4;
import gov.nasa.worldwind.pick.PickSupport;
import gov.nasa.worldwind.render.DrawContext;
import gov.nasa.worldwind.render.OrderedRenderable;
import java.awt.Color;
import java.awt.Point;
import javax.media.opengl.GL2;

/**
 *
 * @author omnis
 */
public class OrderedCube extends Cube implements OrderedRenderable{
    protected Vec4 placePoint;
    protected double eyeDistance;
    protected Extent extent;

    public OrderedCube(Position position, double sizeInMeters) {
        super(position, sizeInMeters);
    }

    @Override
    public double getDistanceFromEye() {
        return this.eyeDistance;
    }

    @Override
    public void pick(DrawContext dc, Point pickPoint) {
        this.render(dc);
    }

    @Override
    public void render(DrawContext dc) {
        OrderedCube orderedCube = this.makeOrderedRenderable(dc);
        
        dc.addOrderedRenderable(orderedCube);
    }   
    
    protected OrderedCube makeOrderedRenderable(DrawContext dc) {
        
        Position pos = Position.fromDegrees(32.762999, -117.089078, 5000f);
        
        OrderedCube orderedCube = new OrderedCube(pos, 5000f);
        
        orderedCube.placePoint = dc.getGlobe().computePointFromPosition(this.position);
        
        return orderedCube;
    }
    
    protected void drawOrderedRenderable(DrawContext dc, PickSupport pickCandidates) {
        
        //set up drawing state
        this.beginDrawing(dc);
        
        try {
            //get the gl interface that is held within the worldwind drawContext
            GL2 gl = dc.getGL().getGL2();
            if(dc.isPickingMode()) {
                Color pickColor = dc.getUniquePickColor();
                pickCandidates.addPickableObject(pickColor.getRGB(), this, this.position);
                gl.glColor3ub((byte)pickColor.getRed(), (byte) pickColor.getGreen(), (byte)pickColor.getBlue());
            }
            
            /*
            //transform cube to globe basis vectors
            gl.glMatrixMode(GL2.GL_MODELVIEW);

            Matrix matrix = dc.getGlobe().computeSurfaceOrientationAtPosition(this.position);
            matrix = dc.getView().getModelviewMatrix().multiply(matrix);

            //look into matrix stack: https://www.gamedev.net/forums/topic/501591-glscaled/
            double[] matrixArray = new double[16];
            matrix.toArray(matrixArray, 0, false);
            gl.glLoadMatrixd(matrixArray, 0);
            */
                    
            //draw the cube
            gl.glScaled(this.size, this.size, this.size);            
            this.drawUnitCube(dc);
        }
        finally {
            this.endDrawing(dc);
        }
    }
}
