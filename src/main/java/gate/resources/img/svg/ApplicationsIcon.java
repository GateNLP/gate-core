package gate.resources.img.svg;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class has been automatically generated using <a
 * href="http://englishjavadrinker.blogspot.com/search/label/SVGRoundTrip">SVGRoundTrip</a>.
 */
@SuppressWarnings("unused")
public class ApplicationsIcon implements
		javax.swing.Icon {
	/**
	 * Paints the transcoded SVG image on the specified graphics context. You
	 * can install a custom transformation on the graphics context to scale the
	 * image.
	 * 
	 * @param g
	 *            Graphics context.
	 */
	public static void paint(Graphics2D g) {
        Shape shape = null;
        Paint paint = null;
        Stroke stroke = null;
        Area clip = null;
         
        float origAlpha = 1.0f;
        Composite origComposite = g.getComposite();
        if (origComposite instanceof AlphaComposite) {
            AlphaComposite origAlphaComposite = 
                (AlphaComposite)origComposite;
            if (origAlphaComposite.getRule() == AlphaComposite.SRC_OVER) {
                origAlpha = origAlphaComposite.getAlpha();
            }
        }
        
	    Shape clip_ = g.getClip();
AffineTransform defaultTransform_ = g.getTransform();
//  is CompositeGraphicsNode
float alpha__0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0 = g.getClip();
AffineTransform defaultTransform__0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
clip = new Area(g.getClip());
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,64.0,64.0)));
g.setClip(clip);
// _0 is CompositeGraphicsNode
float alpha__0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0 = g.getClip();
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0 is CompositeGraphicsNode
float alpha__0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -1.3457649946212769f, -3.6447529792785645f));
// _0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(0.5496010184288025f, 0.7062069773674011f, -0.6977810263633728f, 0.5562379956245422f, 26.523630142211914f, -19.788820266723633f));
// _0_0_0_0 is ShapeNode
paint = new Color(0, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(35.454544, 31.636364);
((GeneralPath)shape).curveTo(35.454544, 33.343426, 34.111397, 34.727272, 32.454544, 34.727272);
((GeneralPath)shape).curveTo(30.79769, 34.727272, 29.454544, 33.343426, 29.454544, 31.636364);
((GeneralPath)shape).curveTo(29.454544, 29.929302, 30.79769, 28.545456, 32.454544, 28.545456);
((GeneralPath)shape).curveTo(34.111397, 28.545456, 35.454544, 29.929302, 35.454544, 31.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_0;
g.setTransform(defaultTransform__0_0_0_0);
g.setClip(clip__0_0_0_0);
float alpha__0_0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1 is ShapeNode
paint = new Color(255, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.09375, 4.59375);
((GeneralPath)shape).curveTo(13.244158, 5.781618, 21.069134, 13.490205, 17.96875, 16.0625);
((GeneralPath)shape).curveTo(14.868366, 18.634794, 8.760565, 9.471451, 6.875, 13.03125);
((GeneralPath)shape).curveTo(4.9894347, 16.59105, 16.003633, 16.488544, 15.625, 20.5);
((GeneralPath)shape).curveTo(15.246366, 24.511457, 4.4121246, 22.367374, 5.59375, 26.21875);
((GeneralPath)shape).curveTo(6.7753754, 30.070126, 14.528686, 22.238714, 17.09375, 25.34375);
((GeneralPath)shape).curveTo(19.658813, 28.448786, 10.503517, 34.549747, 14.0625, 36.4375);
((GeneralPath)shape).curveTo(17.621483, 38.325253, 17.52045, 27.313276, 21.53125, 27.6875);
((GeneralPath)shape).curveTo(25.542051, 28.061724, 23.39731, 38.86644, 27.25, 37.6875);
((GeneralPath)shape).curveTo(31.102692, 36.50856, 23.274061, 28.788654, 26.375, 26.21875);
((GeneralPath)shape).curveTo(29.475939, 23.648846, 35.584667, 32.81367, 37.46875, 29.25);
((GeneralPath)shape).curveTo(39.352833, 25.686329, 28.339483, 25.762918, 28.71875, 21.75);
((GeneralPath)shape).curveTo(29.098017, 17.737082, 39.876118, 19.880157, 38.6875, 16.03125);
((GeneralPath)shape).curveTo(37.498886, 12.182344, 29.817186, 20.041517, 27.25, 16.9375);
((GeneralPath)shape).curveTo(24.682814, 13.833482, 33.812073, 7.693837, 30.25, 5.8125);
((GeneralPath)shape).curveTo(26.687927, 3.931163, 26.792248, 14.944821, 22.78125, 14.5625);
((GeneralPath)shape).curveTo(18.770252, 14.180179, 20.943342, 3.405882, 17.09375, 4.59375);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(22.34375, 15.96875);
((GeneralPath)shape).curveTo(25.021523, 15.96875, 27.21875, 18.086975, 27.21875, 20.71875);
((GeneralPath)shape).curveTo(27.218752, 23.350523, 25.021523, 25.5, 22.34375, 25.5);
((GeneralPath)shape).curveTo(19.665981, 25.5, 17.5, 23.350525, 17.5, 20.71875);
((GeneralPath)shape).curveTo(17.5, 18.086975, 19.665981, 15.96875, 22.34375, 15.96875);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.02955f,0,2,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.09375, 4.59375);
((GeneralPath)shape).curveTo(13.244158, 5.781618, 21.069134, 13.490205, 17.96875, 16.0625);
((GeneralPath)shape).curveTo(14.868366, 18.634794, 8.760565, 9.471451, 6.875, 13.03125);
((GeneralPath)shape).curveTo(4.9894347, 16.59105, 16.003633, 16.488544, 15.625, 20.5);
((GeneralPath)shape).curveTo(15.246366, 24.511457, 4.4121246, 22.367374, 5.59375, 26.21875);
((GeneralPath)shape).curveTo(6.7753754, 30.070126, 14.528686, 22.238714, 17.09375, 25.34375);
((GeneralPath)shape).curveTo(19.658813, 28.448786, 10.503517, 34.549747, 14.0625, 36.4375);
((GeneralPath)shape).curveTo(17.621483, 38.325253, 17.52045, 27.313276, 21.53125, 27.6875);
((GeneralPath)shape).curveTo(25.542051, 28.061724, 23.39731, 38.86644, 27.25, 37.6875);
((GeneralPath)shape).curveTo(31.102692, 36.50856, 23.274061, 28.788654, 26.375, 26.21875);
((GeneralPath)shape).curveTo(29.475939, 23.648846, 35.584667, 32.81367, 37.46875, 29.25);
((GeneralPath)shape).curveTo(39.352833, 25.686329, 28.339483, 25.762918, 28.71875, 21.75);
((GeneralPath)shape).curveTo(29.098017, 17.737082, 39.876118, 19.880157, 38.6875, 16.03125);
((GeneralPath)shape).curveTo(37.498886, 12.182344, 29.817186, 20.041517, 27.25, 16.9375);
((GeneralPath)shape).curveTo(24.682814, 13.833482, 33.812073, 7.693837, 30.25, 5.8125);
((GeneralPath)shape).curveTo(26.687927, 3.931163, 26.792248, 14.944821, 22.78125, 14.5625);
((GeneralPath)shape).curveTo(18.770252, 14.180179, 20.943342, 3.405882, 17.09375, 4.59375);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(22.34375, 15.96875);
((GeneralPath)shape).curveTo(25.021523, 15.96875, 27.21875, 18.086975, 27.21875, 20.71875);
((GeneralPath)shape).curveTo(27.218752, 23.350523, 25.021523, 25.5, 22.34375, 25.5);
((GeneralPath)shape).curveTo(19.665981, 25.5, 17.5, 23.350525, 17.5, 20.71875);
((GeneralPath)shape).curveTo(17.5, 18.086975, 19.665981, 15.96875, 22.34375, 15.96875);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_1;
g.setTransform(defaultTransform__0_0_0_1);
g.setClip(clip__0_0_0_1);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
float alpha__0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(0.9633870124816895f, 0.26811298727989197f, -0.26811298727989197f, 0.9633870124816895f, 30.81049919128418f, 3.225944995880127f));
// _0_0_1 is CompositeGraphicsNode
float alpha__0_0_1_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_0 = g.getClip();
AffineTransform defaultTransform__0_0_1_0 = g.getTransform();
g.transform(new AffineTransform(0.5496010184288025f, 0.7062069773674011f, -0.6977810263633728f, 0.5562379956245422f, 26.523630142211914f, -19.788820266723633f));
// _0_0_1_0 is ShapeNode
paint = new Color(0, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(35.454544, 31.636364);
((GeneralPath)shape).curveTo(35.454544, 33.343426, 34.111397, 34.727272, 32.454544, 34.727272);
((GeneralPath)shape).curveTo(30.79769, 34.727272, 29.454544, 33.343426, 29.454544, 31.636364);
((GeneralPath)shape).curveTo(29.454544, 29.929302, 30.79769, 28.545456, 32.454544, 28.545456);
((GeneralPath)shape).curveTo(34.111397, 28.545456, 35.454544, 29.929302, 35.454544, 31.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_1_0;
g.setTransform(defaultTransform__0_0_1_0);
g.setClip(clip__0_0_1_0);
float alpha__0_0_1_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_1 = g.getClip();
AffineTransform defaultTransform__0_0_1_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_1 is ShapeNode
paint = new Color(255, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.09375, 4.59375);
((GeneralPath)shape).curveTo(13.244158, 5.781618, 21.069134, 13.490205, 17.96875, 16.0625);
((GeneralPath)shape).curveTo(14.868366, 18.634794, 8.760565, 9.471451, 6.875, 13.03125);
((GeneralPath)shape).curveTo(4.9894347, 16.59105, 16.003633, 16.488544, 15.625, 20.5);
((GeneralPath)shape).curveTo(15.246366, 24.511457, 4.4121246, 22.367374, 5.59375, 26.21875);
((GeneralPath)shape).curveTo(6.7753754, 30.070126, 14.528686, 22.238714, 17.09375, 25.34375);
((GeneralPath)shape).curveTo(19.658813, 28.448786, 10.503517, 34.549747, 14.0625, 36.4375);
((GeneralPath)shape).curveTo(17.621483, 38.325253, 17.52045, 27.313276, 21.53125, 27.6875);
((GeneralPath)shape).curveTo(25.542051, 28.061724, 23.39731, 38.86644, 27.25, 37.6875);
((GeneralPath)shape).curveTo(31.102692, 36.50856, 23.274061, 28.788654, 26.375, 26.21875);
((GeneralPath)shape).curveTo(29.475939, 23.648846, 35.584667, 32.81367, 37.46875, 29.25);
((GeneralPath)shape).curveTo(39.352833, 25.686329, 28.339483, 25.762918, 28.71875, 21.75);
((GeneralPath)shape).curveTo(29.098017, 17.737082, 39.876118, 19.880157, 38.6875, 16.03125);
((GeneralPath)shape).curveTo(37.498886, 12.182344, 29.817186, 20.041517, 27.25, 16.9375);
((GeneralPath)shape).curveTo(24.682814, 13.833482, 33.812073, 7.693837, 30.25, 5.8125);
((GeneralPath)shape).curveTo(26.687927, 3.931163, 26.792248, 14.944821, 22.78125, 14.5625);
((GeneralPath)shape).curveTo(18.770252, 14.180179, 20.943342, 3.405882, 17.09375, 4.59375);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(22.34375, 15.96875);
((GeneralPath)shape).curveTo(25.021523, 15.96875, 27.21875, 18.086975, 27.21875, 20.71875);
((GeneralPath)shape).curveTo(27.218752, 23.350523, 25.021523, 25.5, 22.34375, 25.5);
((GeneralPath)shape).curveTo(19.665981, 25.5, 17.5, 23.350525, 17.5, 20.71875);
((GeneralPath)shape).curveTo(17.5, 18.086975, 19.665981, 15.96875, 22.34375, 15.96875);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.02955f,0,2,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.09375, 4.59375);
((GeneralPath)shape).curveTo(13.244158, 5.781618, 21.069134, 13.490205, 17.96875, 16.0625);
((GeneralPath)shape).curveTo(14.868366, 18.634794, 8.760565, 9.471451, 6.875, 13.03125);
((GeneralPath)shape).curveTo(4.9894347, 16.59105, 16.003633, 16.488544, 15.625, 20.5);
((GeneralPath)shape).curveTo(15.246366, 24.511457, 4.4121246, 22.367374, 5.59375, 26.21875);
((GeneralPath)shape).curveTo(6.7753754, 30.070126, 14.528686, 22.238714, 17.09375, 25.34375);
((GeneralPath)shape).curveTo(19.658813, 28.448786, 10.503517, 34.549747, 14.0625, 36.4375);
((GeneralPath)shape).curveTo(17.621483, 38.325253, 17.52045, 27.313276, 21.53125, 27.6875);
((GeneralPath)shape).curveTo(25.542051, 28.061724, 23.39731, 38.86644, 27.25, 37.6875);
((GeneralPath)shape).curveTo(31.102692, 36.50856, 23.274061, 28.788654, 26.375, 26.21875);
((GeneralPath)shape).curveTo(29.475939, 23.648846, 35.584667, 32.81367, 37.46875, 29.25);
((GeneralPath)shape).curveTo(39.352833, 25.686329, 28.339483, 25.762918, 28.71875, 21.75);
((GeneralPath)shape).curveTo(29.098017, 17.737082, 39.876118, 19.880157, 38.6875, 16.03125);
((GeneralPath)shape).curveTo(37.498886, 12.182344, 29.817186, 20.041517, 27.25, 16.9375);
((GeneralPath)shape).curveTo(24.682814, 13.833482, 33.812073, 7.693837, 30.25, 5.8125);
((GeneralPath)shape).curveTo(26.687927, 3.931163, 26.792248, 14.944821, 22.78125, 14.5625);
((GeneralPath)shape).curveTo(18.770252, 14.180179, 20.943342, 3.405882, 17.09375, 4.59375);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(22.34375, 15.96875);
((GeneralPath)shape).curveTo(25.021523, 15.96875, 27.21875, 18.086975, 27.21875, 20.71875);
((GeneralPath)shape).curveTo(27.218752, 23.350523, 25.021523, 25.5, 22.34375, 25.5);
((GeneralPath)shape).curveTo(19.665981, 25.5, 17.5, 23.350525, 17.5, 20.71875);
((GeneralPath)shape).curveTo(17.5, 18.086975, 19.665981, 15.96875, 22.34375, 15.96875);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_1_1;
g.setTransform(defaultTransform__0_0_1_1);
g.setClip(clip__0_0_1_1);
origAlpha = alpha__0_0_1;
g.setTransform(defaultTransform__0_0_1);
g.setClip(clip__0_0_1);
float alpha__0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(0.9525910019874573f, 0.30425500869750977f, -0.30425500869750977f, 0.9525910019874573f, 5.163568019866943f, 19.293319702148438f));
// _0_0_2 is CompositeGraphicsNode
float alpha__0_0_2_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_0 = g.getClip();
AffineTransform defaultTransform__0_0_2_0 = g.getTransform();
g.transform(new AffineTransform(0.5496010184288025f, 0.7062069773674011f, -0.6977810263633728f, 0.5562379956245422f, 26.523630142211914f, -19.788820266723633f));
// _0_0_2_0 is ShapeNode
paint = new Color(0, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(35.454544, 31.636364);
((GeneralPath)shape).curveTo(35.454544, 33.343426, 34.111397, 34.727272, 32.454544, 34.727272);
((GeneralPath)shape).curveTo(30.79769, 34.727272, 29.454544, 33.343426, 29.454544, 31.636364);
((GeneralPath)shape).curveTo(29.454544, 29.929302, 30.79769, 28.545456, 32.454544, 28.545456);
((GeneralPath)shape).curveTo(34.111397, 28.545456, 35.454544, 29.929302, 35.454544, 31.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_0;
g.setTransform(defaultTransform__0_0_2_0);
g.setClip(clip__0_0_2_0);
float alpha__0_0_2_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_1 = g.getClip();
AffineTransform defaultTransform__0_0_2_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_1 is ShapeNode
paint = new Color(255, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.09375, 4.59375);
((GeneralPath)shape).curveTo(13.244158, 5.781618, 21.069134, 13.490205, 17.96875, 16.0625);
((GeneralPath)shape).curveTo(14.868366, 18.634794, 8.760565, 9.471451, 6.875, 13.03125);
((GeneralPath)shape).curveTo(4.9894347, 16.59105, 16.003633, 16.488544, 15.625, 20.5);
((GeneralPath)shape).curveTo(15.246366, 24.511457, 4.4121246, 22.367374, 5.59375, 26.21875);
((GeneralPath)shape).curveTo(6.7753754, 30.070126, 14.528686, 22.238714, 17.09375, 25.34375);
((GeneralPath)shape).curveTo(19.658813, 28.448786, 10.503517, 34.549747, 14.0625, 36.4375);
((GeneralPath)shape).curveTo(17.621483, 38.325253, 17.52045, 27.313276, 21.53125, 27.6875);
((GeneralPath)shape).curveTo(25.542051, 28.061724, 23.39731, 38.86644, 27.25, 37.6875);
((GeneralPath)shape).curveTo(31.102692, 36.50856, 23.274061, 28.788654, 26.375, 26.21875);
((GeneralPath)shape).curveTo(29.475939, 23.648846, 35.584667, 32.81367, 37.46875, 29.25);
((GeneralPath)shape).curveTo(39.352833, 25.686329, 28.339483, 25.762918, 28.71875, 21.75);
((GeneralPath)shape).curveTo(29.098017, 17.737082, 39.876118, 19.880157, 38.6875, 16.03125);
((GeneralPath)shape).curveTo(37.498886, 12.182344, 29.817186, 20.041517, 27.25, 16.9375);
((GeneralPath)shape).curveTo(24.682814, 13.833482, 33.812073, 7.693837, 30.25, 5.8125);
((GeneralPath)shape).curveTo(26.687927, 3.931163, 26.792248, 14.944821, 22.78125, 14.5625);
((GeneralPath)shape).curveTo(18.770252, 14.180179, 20.943342, 3.405882, 17.09375, 4.59375);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(22.34375, 15.96875);
((GeneralPath)shape).curveTo(25.021523, 15.96875, 27.21875, 18.086975, 27.21875, 20.71875);
((GeneralPath)shape).curveTo(27.218752, 23.350523, 25.021523, 25.5, 22.34375, 25.5);
((GeneralPath)shape).curveTo(19.665981, 25.5, 17.5, 23.350525, 17.5, 20.71875);
((GeneralPath)shape).curveTo(17.5, 18.086975, 19.665981, 15.96875, 22.34375, 15.96875);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.02955f,0,2,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.09375, 4.59375);
((GeneralPath)shape).curveTo(13.244158, 5.781618, 21.069134, 13.490205, 17.96875, 16.0625);
((GeneralPath)shape).curveTo(14.868366, 18.634794, 8.760565, 9.471451, 6.875, 13.03125);
((GeneralPath)shape).curveTo(4.9894347, 16.59105, 16.003633, 16.488544, 15.625, 20.5);
((GeneralPath)shape).curveTo(15.246366, 24.511457, 4.4121246, 22.367374, 5.59375, 26.21875);
((GeneralPath)shape).curveTo(6.7753754, 30.070126, 14.528686, 22.238714, 17.09375, 25.34375);
((GeneralPath)shape).curveTo(19.658813, 28.448786, 10.503517, 34.549747, 14.0625, 36.4375);
((GeneralPath)shape).curveTo(17.621483, 38.325253, 17.52045, 27.313276, 21.53125, 27.6875);
((GeneralPath)shape).curveTo(25.542051, 28.061724, 23.39731, 38.86644, 27.25, 37.6875);
((GeneralPath)shape).curveTo(31.102692, 36.50856, 23.274061, 28.788654, 26.375, 26.21875);
((GeneralPath)shape).curveTo(29.475939, 23.648846, 35.584667, 32.81367, 37.46875, 29.25);
((GeneralPath)shape).curveTo(39.352833, 25.686329, 28.339483, 25.762918, 28.71875, 21.75);
((GeneralPath)shape).curveTo(29.098017, 17.737082, 39.876118, 19.880157, 38.6875, 16.03125);
((GeneralPath)shape).curveTo(37.498886, 12.182344, 29.817186, 20.041517, 27.25, 16.9375);
((GeneralPath)shape).curveTo(24.682814, 13.833482, 33.812073, 7.693837, 30.25, 5.8125);
((GeneralPath)shape).curveTo(26.687927, 3.931163, 26.792248, 14.944821, 22.78125, 14.5625);
((GeneralPath)shape).curveTo(18.770252, 14.180179, 20.943342, 3.405882, 17.09375, 4.59375);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(22.34375, 15.96875);
((GeneralPath)shape).curveTo(25.021523, 15.96875, 27.21875, 18.086975, 27.21875, 20.71875);
((GeneralPath)shape).curveTo(27.218752, 23.350523, 25.021523, 25.5, 22.34375, 25.5);
((GeneralPath)shape).curveTo(19.665981, 25.5, 17.5, 23.350525, 17.5, 20.71875);
((GeneralPath)shape).curveTo(17.5, 18.086975, 19.665981, 15.96875, 22.34375, 15.96875);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2_1;
g.setTransform(defaultTransform__0_0_2_1);
g.setClip(clip__0_0_2_1);
origAlpha = alpha__0_0_2;
g.setTransform(defaultTransform__0_0_2);
g.setClip(clip__0_0_2);
origAlpha = alpha__0_0;
g.setTransform(defaultTransform__0_0);
g.setClip(clip__0_0);
origAlpha = alpha__0;
g.setTransform(defaultTransform__0);
g.setClip(clip__0);
g.setTransform(defaultTransform_);
g.setClip(clip_);

	}
	
	public Image getImage() {
		BufferedImage image =
            new BufferedImage(getIconWidth(), getIconHeight(),
                    BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g = image.createGraphics();
    	paintIcon(null, g, 0, 0);
    	g.dispose();
    	return image;
	}

    /**
     * Returns the X of the bounding box of the original SVG image.
     * 
     * @return The X of the bounding box of the original SVG image.
     */
    public static int getOrigX() {
        return 0;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 0;
    }

	/**
	 * Returns the width of the bounding box of the original SVG image.
	 * 
	 * @return The width of the bounding box of the original SVG image.
	 */
	public static int getOrigWidth() {
		return 64;
	}

	/**
	 * Returns the height of the bounding box of the original SVG image.
	 * 
	 * @return The height of the bounding box of the original SVG image.
	 */
	public static int getOrigHeight() {
		return 64;
	}

	/**
	 * The current width of this resizable icon.
	 */
	int width;

	/**
	 * The current height of this resizable icon.
	 */
	int height;

	/**
	 * Creates a new transcoded SVG image.
	 */
	public ApplicationsIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public ApplicationsIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public ApplicationsIcon(int width, int height) {
	this.width = width;
	this.height = height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#getIconHeight()
	 */
    @Override
	public int getIconHeight() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#getIconWidth()
	 */
    @Override
	public int getIconWidth() {
		return width;
	}

	public void setDimension(Dimension newDimension) {
		this.width = newDimension.width;
		this.height = newDimension.height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics,
	 * int, int)
	 */
    @Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.translate(x, y);
						
		Area clip = new Area(new Rectangle(0, 0, this.width, this.height));		
		if (g2d.getClip() != null) clip.intersect(new Area(g2d.getClip()));		
		g2d.setClip(clip);

		double coef1 = (double) this.width / (double) getOrigWidth();
		double coef2 = (double) this.height / (double) getOrigHeight();
		double coef = Math.min(coef1, coef2);
		g2d.scale(coef, coef);
		paint(g2d);
		g2d.dispose();
	}
}

