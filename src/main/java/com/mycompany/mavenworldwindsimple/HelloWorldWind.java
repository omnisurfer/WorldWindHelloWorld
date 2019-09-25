/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenworldwindsimple;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;

import gov.nasa.worldwind.*;

import com.mycompany.mavenworldwindsimple.solids.Cube;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.DrawContext;

/**
 * This is the most basic WorldWind program.
 *
 * @version $Id: HelloWorldWind.java 1971 2014-04-29 21:31:28Z dcollins $
 */
public class HelloWorldWind
{
    // An inner class is used rather than directly subclassing JFrame in the main class so
    // that the main can configure system properties prior to invoking Swing. This is
    // necessary for instance on OS X (Macs) so that the application name can be specified.

    private static class AppFrame extends javax.swing.JFrame
    {
        WorldWindowGLCanvas wwd;
        
        public AppFrame()
        {
            this.wwd = new WorldWindowGLCanvas();
            this.wwd.setPreferredSize(new java.awt.Dimension(1000, 800));
            this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            this.getContentPane().add(wwd, java.awt.BorderLayout.CENTER);
            this.pack();

            this.wwd.setModel(new BasicModel());                     
        }
        
        public DrawContext getWorldWindDrawContext() {
            return wwd.getSceneController().getDrawContext();
        }
    }

    public static void main(String[] args)
    {
        if (Configuration.isMacOS())
        {
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Hello WorldWind");
        }

        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                // Create an AppFrame and immediately make it visible. As per Swing convention, this
                // is done within an invokeLater call so that it executes on an AWT thread.
                AppFrame frame = new AppFrame();
                
                frame.setVisible(true);
                
                Position pos = Position.fromDegrees(35, -110, 50000);

                Cube cube = new Cube(pos, 10000f);

                DrawContext dc = frame.getWorldWindDrawContext();

                cube.render(dc);

                System.out.print("END OF PROGRAM");
            }
        });
    }
}