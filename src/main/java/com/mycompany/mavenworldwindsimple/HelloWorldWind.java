/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenworldwindsimple;

import com.mycompany.mavenworldwindsimple.solids.Cube;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.*;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import java.awt.Dimension;
import java.util.concurrent.TimeUnit;

/**
 * This is the most basic WorldWind program. 
 * * README ** README ** README ** README ** README ** README ** README ** README *
 * * README ** README ** README ** README ** README ** README ** README ** README *
 * * README ** README ** README ** README ** README ** README ** README ** README *
 * This is not the default project that the POM is set to build. 
 * That is now HelloWorldWindAppTemplate which
 * better suits the cube example that I was following when I started. This
 * is preserved for historical purposes but is probably not a good example to
 * follow.
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
        Model model;
        
        public AppFrame()
        {            
            this.wwd = new WorldWindowGLCanvas();
            this.wwd.setPreferredSize(new java.awt.Dimension(1000, 800));
            this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            this.getContentPane().add(wwd, java.awt.BorderLayout.CENTER);          
            this.pack();

            this.wwd.setModel(new BasicModel());
            
            RenderableLayer layer = new RenderableLayer();
            
            Position pos = Position.fromDegrees(32.762999, -117.214548, 5000f);

            Cube cube = new Cube(pos, 10000f);
            
            layer.addRenderable(cube);
            
            this.model = this.wwd.getModel();
            this.model.getLayers().add(layer);
            
        }
                        
        public Model getWorldWindModel() {
            return this.model;
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

                /* add after display test
                 note there is a race condition here because I don't know when the context is valid
                so I am just delaying for a quick and dirty test.
                */             
                System.out.print("0\n");
                try {
                    TimeUnit.SECONDS.sleep(3);
                }
                catch (InterruptedException ex) {
                    //do nothing
                }
                System.out.print("1\n");

                RenderableLayer layer = new RenderableLayer();

                Position pos = Position.fromDegrees(32.762999, -117.089078, 5000f);

                Cube cube = new Cube(pos, 10000f);

                layer.addRenderable(cube);

                Model model = frame.getWorldWindModel();
                model.getLayers().add(layer);

                System.out.print("\nEND OF PROGRAM\n");
            }
        });        
        
    }
}