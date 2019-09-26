/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenworldwindsimple;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwindx.examples.ApplicationTemplate;
import java.awt.Dimension;

/**
 * This class is an example based on a mixture of the cube example (https://worldwind.arc.nasa.gov/java/tutorials/build-a-custom-renderable/)
 * and what I can copy from the GliderTestApp example (GliderTestApp.java)
 * This code is not intended to be used in a production system. It is more a tool
 * to explore how to get things working (but not necessarily in the cleanest or *correct* way).
 * This just allows me to start thinking about the methods and structures needed to see something happen.
 * @author omnis
 */
public class HelloWorldWindAppTemplate extends ApplicationTemplate {
    
    public static class HelloWorldWindAppPanel extends AppPanel {
        
        public HelloWorldWindAppPanel(Dimension canvasSize, boolean includeStatusBar) {
            super(canvasSize, includeStatusBar);
        }
        
        @Override
        protected WorldWindow createWorldWindow() {
            return new HelloWorldWindAppTemplateWindow();
        }
    }
    
    public static class HelloWorldWindAppFrame extends AppFrame {
        public HelloWorldWindAppFrame() {
            super(true, true, false);
        }
        
        @Override
        protected AppPanel createAppPanel(Dimension canvasSize, boolean includeStatusBar) {
            return new HelloWorldWindAppPanel(canvasSize, includeStatusBar);
        }
    }
    
    public static void main(String[] args) {
        
        final AppFrame frame = start("Hello WorldWind App Template Demo", HelloWorldWindAppFrame.class);
        
        ((HelloWorldWindAppTemplateWindow)((HelloWorldWindAppFrame) frame).getWwd()).addCube();
        
    }
}
