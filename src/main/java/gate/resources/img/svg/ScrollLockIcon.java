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
public class ScrollLockIcon implements
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
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,48.0,48.0)));
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0 = origAlpha;
origAlpha = origAlpha * 0.4f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(0.9999989867210388f, 0.0f, 0.0f, 1.375f, -1.9999959468841553f, -16.375f));
// _0_0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(-1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f));
// _0_0_0_0_0 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(6.0, 43.00000762939453), 1.5f, new Point2D.Double(6.0, 43.00000762939453), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.0f, 0.0f, 0.0f, 1.3333330154418945f, -21.0f, -100.33329772949219f));
shape = new Rectangle2D.Double(-9.0, -45.0, 3.0, 4.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_0_0;
g.setTransform(defaultTransform__0_0_0_0_0);
g.setClip(clip__0_0_0_0_0);
float alpha__0_0_0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_0_1 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(6.0, 43.00000762939453), 1.5f, new Point2D.Double(6.0, 43.00000762939453), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.0f, 0.0f, 0.0f, 1.3333330154418945f, 33.0f, -14.33329963684082f));
shape = new Rectangle2D.Double(45.0, 41.0, 3.0, 4.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_0_1;
g.setTransform(defaultTransform__0_0_0_0_1);
g.setClip(clip__0_0_0_0_1);
float alpha__0_0_0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_0_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(10.53125, 40.999969482421875), new Point2D.Double(10.53125, 45.001102447509766), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(0, 0, 0, 0),new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(9.0f, 0.0f, 0.0f, 1.0f, -72.0f, 0.0f));
shape = new Rectangle2D.Double(9.0, 41.0, 36.0, 4.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_0_2;
g.setTransform(defaultTransform__0_0_0_0_2);
g.setClip(clip__0_0_0_0_2);
origAlpha = alpha__0_0_0_0;
g.setTransform(defaultTransform__0_0_0_0);
g.setClip(clip__0_0_0_0);
float alpha__0_0_0_1 = origAlpha;
origAlpha = origAlpha * 0.08235294f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_1 = g.getTransform();
g.transform(new AffineTransform(0.8296719789505005f, 0.0f, 0.0f, 0.8110929727554321f, -12.99530029296875f, 17.827489852905273f));
// _0_0_0_1 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(28.284271240234375, 30.145553588867188), 13.258252f, new Point2D.Double(28.284271240234375, 30.145553588867188), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.3400000035762787f, 0.0f, 19.89607048034668f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(41.542522, 30.145554);
((GeneralPath)shape).curveTo(41.543793, 31.756325, 39.017063, 33.244926, 34.914425, 34.050434);
((GeneralPath)shape).curveTo(30.811785, 34.855946, 25.756758, 34.855946, 21.654118, 34.050434);
((GeneralPath)shape).curveTo(17.55148, 33.244926, 15.024749, 31.756325, 15.026019, 30.145554);
((GeneralPath)shape).curveTo(15.024749, 28.534782, 17.55148, 27.046183, 21.654118, 26.240671);
((GeneralPath)shape).curveTo(25.756758, 25.435162, 30.811785, 25.435162, 34.914425, 26.240671);
((GeneralPath)shape).curveTo(39.017063, 27.046183, 41.543793, 28.534782, 41.542522, 30.145554);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_1;
g.setTransform(defaultTransform__0_0_0_1);
g.setClip(clip__0_0_0_1);
float alpha__0_0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(17.5, 6.5), new Point2D.Double(17.5, 40.7536506652832), new float[] {0.0f,0.12643678f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(238, 238, 236, 255),new Color(255, 255, 255, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(9.0, 4.5);
((GeneralPath)shape).curveTo(8.164637, 4.5, 7.5, 8.073056, 7.5, 12.5);
((GeneralPath)shape).lineTo(7.5, 43.5);
((GeneralPath)shape).lineTo(42.5, 43.5);
((GeneralPath)shape).lineTo(42.5, 12.5);
((GeneralPath)shape).curveTo(42.5, 8.073057, 41.835365, 4.5, 41.0, 4.5);
((GeneralPath)shape).lineTo(39.3125, 4.5);
((GeneralPath)shape).lineTo(39.75, 5.5);
((GeneralPath)shape).lineTo(37.75, 5.5);
((GeneralPath)shape).lineTo(37.3125, 4.5);
((GeneralPath)shape).lineTo(12.65625, 4.5);
((GeneralPath)shape).lineTo(12.21875, 5.5);
((GeneralPath)shape).lineTo(10.21875, 5.5);
((GeneralPath)shape).lineTo(10.65625, 4.5);
((GeneralPath)shape).lineTo(9.0, 4.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.75, 7.5);
((GeneralPath)shape).lineTo(11.75, 7.5);
((GeneralPath)shape).lineTo(11.5625, 9.5);
((GeneralPath)shape).lineTo(9.5625, 9.5);
((GeneralPath)shape).lineTo(9.75, 7.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.1875, 7.5);
((GeneralPath)shape).lineTo(40.1875, 7.5);
((GeneralPath)shape).lineTo(40.40625, 9.5);
((GeneralPath)shape).lineTo(38.40625, 9.5);
((GeneralPath)shape).lineTo(38.1875, 7.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 11.5);
((GeneralPath)shape).lineTo(11.5, 11.5);
((GeneralPath)shape).lineTo(11.5, 13.5);
((GeneralPath)shape).lineTo(9.5, 13.5);
((GeneralPath)shape).lineTo(9.5, 11.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 11.5);
((GeneralPath)shape).lineTo(40.5, 11.5);
((GeneralPath)shape).lineTo(40.5, 13.5);
((GeneralPath)shape).lineTo(38.5, 13.5);
((GeneralPath)shape).lineTo(38.5, 11.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 15.5);
((GeneralPath)shape).lineTo(11.5, 15.5);
((GeneralPath)shape).lineTo(11.5, 17.5);
((GeneralPath)shape).lineTo(9.5, 17.5);
((GeneralPath)shape).lineTo(9.5, 15.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 15.5);
((GeneralPath)shape).lineTo(40.5, 15.5);
((GeneralPath)shape).lineTo(40.5, 17.5);
((GeneralPath)shape).lineTo(38.5, 17.5);
((GeneralPath)shape).lineTo(38.5, 15.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 19.5);
((GeneralPath)shape).lineTo(11.5, 19.5);
((GeneralPath)shape).lineTo(11.5, 21.5);
((GeneralPath)shape).lineTo(9.5, 21.5);
((GeneralPath)shape).lineTo(9.5, 19.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 19.5);
((GeneralPath)shape).lineTo(40.5, 19.5);
((GeneralPath)shape).lineTo(40.5, 21.5);
((GeneralPath)shape).lineTo(38.5, 21.5);
((GeneralPath)shape).lineTo(38.5, 19.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 23.5);
((GeneralPath)shape).lineTo(11.5, 23.5);
((GeneralPath)shape).lineTo(11.5, 25.5);
((GeneralPath)shape).lineTo(9.5, 25.5);
((GeneralPath)shape).lineTo(9.5, 23.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 23.5);
((GeneralPath)shape).lineTo(40.5, 23.5);
((GeneralPath)shape).lineTo(40.5, 25.5);
((GeneralPath)shape).lineTo(38.5, 25.5);
((GeneralPath)shape).lineTo(38.5, 23.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 27.5);
((GeneralPath)shape).lineTo(11.5, 27.5);
((GeneralPath)shape).lineTo(11.5, 29.5);
((GeneralPath)shape).lineTo(9.5, 29.5);
((GeneralPath)shape).lineTo(9.5, 27.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 27.5);
((GeneralPath)shape).lineTo(40.5, 27.5);
((GeneralPath)shape).lineTo(40.5, 29.5);
((GeneralPath)shape).lineTo(38.5, 29.5);
((GeneralPath)shape).lineTo(38.5, 27.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 31.5);
((GeneralPath)shape).lineTo(11.5, 31.5);
((GeneralPath)shape).lineTo(11.5, 33.5);
((GeneralPath)shape).lineTo(9.5, 33.5);
((GeneralPath)shape).lineTo(9.5, 31.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 31.5);
((GeneralPath)shape).lineTo(40.5, 31.5);
((GeneralPath)shape).lineTo(40.5, 33.5);
((GeneralPath)shape).lineTo(38.5, 33.5);
((GeneralPath)shape).lineTo(38.5, 31.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 35.5);
((GeneralPath)shape).lineTo(11.5, 35.5);
((GeneralPath)shape).lineTo(11.5, 37.5);
((GeneralPath)shape).lineTo(9.5, 37.5);
((GeneralPath)shape).lineTo(9.5, 35.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 35.5);
((GeneralPath)shape).lineTo(40.5, 35.5);
((GeneralPath)shape).lineTo(40.5, 37.5);
((GeneralPath)shape).lineTo(38.5, 37.5);
((GeneralPath)shape).lineTo(38.5, 35.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(136, 138, 133, 255);
stroke = new BasicStroke(1.0000005f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(9.0, 4.5);
((GeneralPath)shape).curveTo(8.164637, 4.5, 7.5, 8.073056, 7.5, 12.5);
((GeneralPath)shape).lineTo(7.5, 43.5);
((GeneralPath)shape).lineTo(42.5, 43.5);
((GeneralPath)shape).lineTo(42.5, 12.5);
((GeneralPath)shape).curveTo(42.5, 8.073057, 41.835365, 4.5, 41.0, 4.5);
((GeneralPath)shape).lineTo(39.3125, 4.5);
((GeneralPath)shape).lineTo(39.75, 5.5);
((GeneralPath)shape).lineTo(37.75, 5.5);
((GeneralPath)shape).lineTo(37.3125, 4.5);
((GeneralPath)shape).lineTo(12.65625, 4.5);
((GeneralPath)shape).lineTo(12.21875, 5.5);
((GeneralPath)shape).lineTo(10.21875, 5.5);
((GeneralPath)shape).lineTo(10.65625, 4.5);
((GeneralPath)shape).lineTo(9.0, 4.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.75, 7.5);
((GeneralPath)shape).lineTo(11.75, 7.5);
((GeneralPath)shape).lineTo(11.5625, 9.5);
((GeneralPath)shape).lineTo(9.5625, 9.5);
((GeneralPath)shape).lineTo(9.75, 7.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.1875, 7.5);
((GeneralPath)shape).lineTo(40.1875, 7.5);
((GeneralPath)shape).lineTo(40.40625, 9.5);
((GeneralPath)shape).lineTo(38.40625, 9.5);
((GeneralPath)shape).lineTo(38.1875, 7.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 11.5);
((GeneralPath)shape).lineTo(11.5, 11.5);
((GeneralPath)shape).lineTo(11.5, 13.5);
((GeneralPath)shape).lineTo(9.5, 13.5);
((GeneralPath)shape).lineTo(9.5, 11.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 11.5);
((GeneralPath)shape).lineTo(40.5, 11.5);
((GeneralPath)shape).lineTo(40.5, 13.5);
((GeneralPath)shape).lineTo(38.5, 13.5);
((GeneralPath)shape).lineTo(38.5, 11.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 15.5);
((GeneralPath)shape).lineTo(11.5, 15.5);
((GeneralPath)shape).lineTo(11.5, 17.5);
((GeneralPath)shape).lineTo(9.5, 17.5);
((GeneralPath)shape).lineTo(9.5, 15.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 15.5);
((GeneralPath)shape).lineTo(40.5, 15.5);
((GeneralPath)shape).lineTo(40.5, 17.5);
((GeneralPath)shape).lineTo(38.5, 17.5);
((GeneralPath)shape).lineTo(38.5, 15.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 19.5);
((GeneralPath)shape).lineTo(11.5, 19.5);
((GeneralPath)shape).lineTo(11.5, 21.5);
((GeneralPath)shape).lineTo(9.5, 21.5);
((GeneralPath)shape).lineTo(9.5, 19.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 19.5);
((GeneralPath)shape).lineTo(40.5, 19.5);
((GeneralPath)shape).lineTo(40.5, 21.5);
((GeneralPath)shape).lineTo(38.5, 21.5);
((GeneralPath)shape).lineTo(38.5, 19.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 23.5);
((GeneralPath)shape).lineTo(11.5, 23.5);
((GeneralPath)shape).lineTo(11.5, 25.5);
((GeneralPath)shape).lineTo(9.5, 25.5);
((GeneralPath)shape).lineTo(9.5, 23.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 23.5);
((GeneralPath)shape).lineTo(40.5, 23.5);
((GeneralPath)shape).lineTo(40.5, 25.5);
((GeneralPath)shape).lineTo(38.5, 25.5);
((GeneralPath)shape).lineTo(38.5, 23.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 27.5);
((GeneralPath)shape).lineTo(11.5, 27.5);
((GeneralPath)shape).lineTo(11.5, 29.5);
((GeneralPath)shape).lineTo(9.5, 29.5);
((GeneralPath)shape).lineTo(9.5, 27.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 27.5);
((GeneralPath)shape).lineTo(40.5, 27.5);
((GeneralPath)shape).lineTo(40.5, 29.5);
((GeneralPath)shape).lineTo(38.5, 29.5);
((GeneralPath)shape).lineTo(38.5, 27.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 31.5);
((GeneralPath)shape).lineTo(11.5, 31.5);
((GeneralPath)shape).lineTo(11.5, 33.5);
((GeneralPath)shape).lineTo(9.5, 33.5);
((GeneralPath)shape).lineTo(9.5, 31.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 31.5);
((GeneralPath)shape).lineTo(40.5, 31.5);
((GeneralPath)shape).lineTo(40.5, 33.5);
((GeneralPath)shape).lineTo(38.5, 33.5);
((GeneralPath)shape).lineTo(38.5, 31.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 35.5);
((GeneralPath)shape).lineTo(11.5, 35.5);
((GeneralPath)shape).lineTo(11.5, 37.5);
((GeneralPath)shape).lineTo(9.5, 37.5);
((GeneralPath)shape).lineTo(9.5, 35.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 35.5);
((GeneralPath)shape).lineTo(40.5, 35.5);
((GeneralPath)shape).lineTo(40.5, 37.5);
((GeneralPath)shape).lineTo(38.5, 37.5);
((GeneralPath)shape).lineTo(38.5, 35.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_2;
g.setTransform(defaultTransform__0_0_0_2);
g.setClip(clip__0_0_0_2);
float alpha__0_0_0_3 = origAlpha;
origAlpha = origAlpha * 0.1f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_3 = g.getClip();
AffineTransform defaultTransform__0_0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_3 is ShapeNode
paint = new Color(136, 138, 133, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(9.0625, 5.0);
((GeneralPath)shape).curveTo(9.022063, 5.043827, 8.951063, 5.172764, 8.875, 5.34375);
((GeneralPath)shape).curveTo(8.798937, 5.514736, 8.73023, 5.7318797, 8.65625, 6.0);
((GeneralPath)shape).lineTo(10.21875, 6.0);
((GeneralPath)shape).curveTo(10.051578, 6.00173, 9.906679, 5.9193797, 9.8125, 5.78125);
((GeneralPath)shape).curveTo(9.718321, 5.6431203, 9.687321, 5.467487, 9.75, 5.3125);
((GeneralPath)shape).lineTo(9.875, 5.0);
((GeneralPath)shape).lineTo(9.0625, 5.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(13.0, 5.0);
((GeneralPath)shape).lineTo(12.6875, 5.6875);
((GeneralPath)shape).curveTo(12.610377, 5.8781476, 12.424395, 6.0021358, 12.21875, 6.0);
((GeneralPath)shape).lineTo(37.75, 6.0);
((GeneralPath)shape).curveTo(37.544353, 6.0021358, 37.35837, 5.8781476, 37.28125, 5.6875);
((GeneralPath)shape).lineTo(36.96875, 5.0);
((GeneralPath)shape).lineTo(13.0, 5.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(40.09375, 5.0);
((GeneralPath)shape).lineTo(40.21875, 5.3125);
((GeneralPath)shape).curveTo(40.28143, 5.467487, 40.250427, 5.6431203, 40.15625, 5.78125);
((GeneralPath)shape).curveTo(40.06207, 5.9193797, 39.91717, 6.00173, 39.75, 6.0);
((GeneralPath)shape).lineTo(41.34375, 6.0);
((GeneralPath)shape).curveTo(41.26977, 5.7318797, 41.20106, 5.514736, 41.125, 5.34375);
((GeneralPath)shape).curveTo(41.04894, 5.172764, 40.977936, 5.043827, 40.9375, 5.0);
((GeneralPath)shape).lineTo(40.09375, 5.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(8.25, 8.0);
((GeneralPath)shape).curveTo(8.170528, 8.601359, 8.140286, 9.285213, 8.09375, 10.0);
((GeneralPath)shape).lineTo(9.5625, 10.0);
((GeneralPath)shape).curveTo(9.4243, 10.000255, 9.282217, 9.944387, 9.1875, 9.84375);
((GeneralPath)shape).curveTo(9.092783, 9.743113, 9.053877, 9.606681, 9.0625, 9.46875);
((GeneralPath)shape).lineTo(9.1875, 8.0);
((GeneralPath)shape).lineTo(8.25, 8.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(12.21875, 8.0);
((GeneralPath)shape).lineTo(12.0625, 9.53125);
((GeneralPath)shape).curveTo(12.045952, 9.795107, 11.826875, 10.000492, 11.5625, 10.0);
((GeneralPath)shape).lineTo(38.40625, 10.0);
((GeneralPath)shape).curveTo(38.15279, 10.001965, 37.937946, 9.813978, 37.90625, 9.5625);
((GeneralPath)shape).lineTo(37.75, 8.0);
((GeneralPath)shape).lineTo(12.21875, 8.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(40.75, 8.0);
((GeneralPath)shape).lineTo(40.90625, 9.4375);
((GeneralPath)shape).curveTo(40.92426, 9.580553, 40.87704, 9.735988, 40.78125, 9.84375);
((GeneralPath)shape).curveTo(40.68546, 9.951513, 40.550426, 10.001115, 40.40625, 10.0);
((GeneralPath)shape).lineTo(41.90625, 10.0);
((GeneralPath)shape).curveTo(41.859715, 9.285213, 41.82947, 8.601359, 41.75, 8.0);
((GeneralPath)shape).lineTo(40.75, 8.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(8.0, 12.0);
((GeneralPath)shape).curveTo(7.997886, 12.168756, 8.0, 12.328423, 8.0, 12.5);
((GeneralPath)shape).lineTo(8.0, 14.0);
((GeneralPath)shape).lineTo(9.5, 14.0);
((GeneralPath)shape).curveTo(9.223869, 13.999972, 9.000028, 13.776131, 9.0, 13.5);
((GeneralPath)shape).lineTo(9.0, 12.0);
((GeneralPath)shape).lineTo(8.0, 12.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(12.0, 12.0);
((GeneralPath)shape).lineTo(12.0, 13.5);
((GeneralPath)shape).curveTo(11.999972, 13.776131, 11.776131, 13.999972, 11.5, 14.0);
((GeneralPath)shape).lineTo(38.5, 14.0);
((GeneralPath)shape).curveTo(38.22387, 13.999972, 38.000027, 13.776131, 38.0, 13.5);
((GeneralPath)shape).lineTo(38.0, 12.0);
((GeneralPath)shape).lineTo(12.0, 12.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(41.0, 12.0);
((GeneralPath)shape).lineTo(41.0, 13.5);
((GeneralPath)shape).curveTo(40.999973, 13.776131, 40.77613, 13.999972, 40.5, 14.0);
((GeneralPath)shape).lineTo(42.0, 14.0);
((GeneralPath)shape).lineTo(42.0, 12.5);
((GeneralPath)shape).curveTo(42.0, 12.328423, 42.002113, 12.168756, 42.0, 12.0);
((GeneralPath)shape).lineTo(41.0, 12.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(8.0, 16.0);
((GeneralPath)shape).lineTo(8.0, 18.0);
((GeneralPath)shape).lineTo(9.5, 18.0);
((GeneralPath)shape).curveTo(9.223869, 17.999971, 9.000028, 17.77613, 9.0, 17.5);
((GeneralPath)shape).lineTo(9.0, 16.0);
((GeneralPath)shape).lineTo(8.0, 16.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(12.0, 16.0);
((GeneralPath)shape).lineTo(12.0, 17.5);
((GeneralPath)shape).curveTo(11.999972, 17.77613, 11.776131, 17.999971, 11.5, 18.0);
((GeneralPath)shape).lineTo(38.5, 18.0);
((GeneralPath)shape).curveTo(38.22387, 17.999971, 38.000027, 17.77613, 38.0, 17.5);
((GeneralPath)shape).lineTo(38.0, 16.0);
((GeneralPath)shape).lineTo(12.0, 16.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(41.0, 16.0);
((GeneralPath)shape).lineTo(41.0, 17.5);
((GeneralPath)shape).curveTo(40.999973, 17.77613, 40.77613, 17.999971, 40.5, 18.0);
((GeneralPath)shape).lineTo(42.0, 18.0);
((GeneralPath)shape).lineTo(42.0, 16.0);
((GeneralPath)shape).lineTo(41.0, 16.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(8.0, 20.0);
((GeneralPath)shape).lineTo(8.0, 22.0);
((GeneralPath)shape).lineTo(9.5, 22.0);
((GeneralPath)shape).curveTo(9.223869, 21.999971, 9.000028, 21.77613, 9.0, 21.5);
((GeneralPath)shape).lineTo(9.0, 20.0);
((GeneralPath)shape).lineTo(8.0, 20.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(12.0, 20.0);
((GeneralPath)shape).lineTo(12.0, 21.5);
((GeneralPath)shape).curveTo(11.999972, 21.77613, 11.776131, 21.999971, 11.5, 22.0);
((GeneralPath)shape).lineTo(38.5, 22.0);
((GeneralPath)shape).curveTo(38.22387, 21.999971, 38.000027, 21.77613, 38.0, 21.5);
((GeneralPath)shape).lineTo(38.0, 20.0);
((GeneralPath)shape).lineTo(12.0, 20.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(41.0, 20.0);
((GeneralPath)shape).lineTo(41.0, 21.5);
((GeneralPath)shape).curveTo(40.999973, 21.77613, 40.77613, 21.999971, 40.5, 22.0);
((GeneralPath)shape).lineTo(42.0, 22.0);
((GeneralPath)shape).lineTo(42.0, 20.0);
((GeneralPath)shape).lineTo(41.0, 20.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(8.0, 24.0);
((GeneralPath)shape).lineTo(8.0, 26.0);
((GeneralPath)shape).lineTo(9.5, 26.0);
((GeneralPath)shape).curveTo(9.223869, 25.999971, 9.000028, 25.77613, 9.0, 25.5);
((GeneralPath)shape).lineTo(9.0, 24.0);
((GeneralPath)shape).lineTo(8.0, 24.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(12.0, 24.0);
((GeneralPath)shape).lineTo(12.0, 25.5);
((GeneralPath)shape).curveTo(11.999972, 25.77613, 11.776131, 25.999971, 11.5, 26.0);
((GeneralPath)shape).lineTo(38.5, 26.0);
((GeneralPath)shape).curveTo(38.22387, 25.999971, 38.000027, 25.77613, 38.0, 25.5);
((GeneralPath)shape).lineTo(38.0, 24.0);
((GeneralPath)shape).lineTo(12.0, 24.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(41.0, 24.0);
((GeneralPath)shape).lineTo(41.0, 25.5);
((GeneralPath)shape).curveTo(40.999973, 25.77613, 40.77613, 25.999971, 40.5, 26.0);
((GeneralPath)shape).lineTo(42.0, 26.0);
((GeneralPath)shape).lineTo(42.0, 24.0);
((GeneralPath)shape).lineTo(41.0, 24.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(8.0, 28.0);
((GeneralPath)shape).lineTo(8.0, 30.0);
((GeneralPath)shape).lineTo(9.5, 30.0);
((GeneralPath)shape).curveTo(9.223869, 29.999971, 9.000028, 29.77613, 9.0, 29.5);
((GeneralPath)shape).lineTo(9.0, 28.0);
((GeneralPath)shape).lineTo(8.0, 28.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(12.0, 28.0);
((GeneralPath)shape).lineTo(12.0, 29.5);
((GeneralPath)shape).curveTo(11.999972, 29.77613, 11.776131, 29.999971, 11.5, 30.0);
((GeneralPath)shape).lineTo(38.5, 30.0);
((GeneralPath)shape).curveTo(38.22387, 29.999971, 38.000027, 29.77613, 38.0, 29.5);
((GeneralPath)shape).lineTo(38.0, 28.0);
((GeneralPath)shape).lineTo(12.0, 28.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(41.0, 28.0);
((GeneralPath)shape).lineTo(41.0, 29.5);
((GeneralPath)shape).curveTo(40.999973, 29.77613, 40.77613, 29.999971, 40.5, 30.0);
((GeneralPath)shape).lineTo(42.0, 30.0);
((GeneralPath)shape).lineTo(42.0, 28.0);
((GeneralPath)shape).lineTo(41.0, 28.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(8.0, 32.0);
((GeneralPath)shape).lineTo(8.0, 34.0);
((GeneralPath)shape).lineTo(9.5, 34.0);
((GeneralPath)shape).curveTo(9.223869, 33.999973, 9.000028, 33.77613, 9.0, 33.5);
((GeneralPath)shape).lineTo(9.0, 32.0);
((GeneralPath)shape).lineTo(8.0, 32.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(12.0, 32.0);
((GeneralPath)shape).lineTo(12.0, 33.5);
((GeneralPath)shape).curveTo(11.999972, 33.77613, 11.776131, 33.999973, 11.5, 34.0);
((GeneralPath)shape).lineTo(38.5, 34.0);
((GeneralPath)shape).curveTo(38.22387, 33.999973, 38.000027, 33.77613, 38.0, 33.5);
((GeneralPath)shape).lineTo(38.0, 32.0);
((GeneralPath)shape).lineTo(12.0, 32.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(41.0, 32.0);
((GeneralPath)shape).lineTo(41.0, 33.5);
((GeneralPath)shape).curveTo(40.999973, 33.77613, 40.77613, 33.999973, 40.5, 34.0);
((GeneralPath)shape).lineTo(42.0, 34.0);
((GeneralPath)shape).lineTo(42.0, 32.0);
((GeneralPath)shape).lineTo(41.0, 32.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(8.0, 36.0);
((GeneralPath)shape).lineTo(8.0, 38.0);
((GeneralPath)shape).lineTo(9.5, 38.0);
((GeneralPath)shape).curveTo(9.223869, 37.999973, 9.000028, 37.77613, 9.0, 37.5);
((GeneralPath)shape).lineTo(9.0, 36.0);
((GeneralPath)shape).lineTo(8.0, 36.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(12.0, 36.0);
((GeneralPath)shape).lineTo(12.0, 37.5);
((GeneralPath)shape).curveTo(11.999972, 37.77613, 11.776131, 37.999973, 11.5, 38.0);
((GeneralPath)shape).lineTo(38.5, 38.0);
((GeneralPath)shape).curveTo(38.22387, 37.999973, 38.000027, 37.77613, 38.0, 37.5);
((GeneralPath)shape).lineTo(38.0, 36.0);
((GeneralPath)shape).lineTo(12.0, 36.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(41.0, 36.0);
((GeneralPath)shape).lineTo(41.0, 37.5);
((GeneralPath)shape).curveTo(40.999973, 37.77613, 40.77613, 37.999973, 40.5, 38.0);
((GeneralPath)shape).lineTo(42.0, 38.0);
((GeneralPath)shape).lineTo(42.0, 36.0);
((GeneralPath)shape).lineTo(41.0, 36.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_3;
g.setTransform(defaultTransform__0_0_0_3);
g.setClip(clip__0_0_0_3);
float alpha__0_0_0_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_4 = g.getClip();
AffineTransform defaultTransform__0_0_0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_4 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(20.625, 10.24703598022461), new Point2D.Double(21.0, 62.17327117919922), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.0f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(13.3125, 5.5);
((GeneralPath)shape).lineTo(13.125, 5.90625);
((GeneralPath)shape).curveTo(12.965567, 6.2648244, 12.61116, 6.4970217, 12.21875, 6.5);
((GeneralPath)shape).lineTo(10.21875, 6.5);
((GeneralPath)shape).curveTo(9.771428, 6.5040383, 9.375829, 6.2105293, 9.25, 5.78125);
((GeneralPath)shape).curveTo(9.138852, 6.0820355, 9.012011, 6.4658103, 8.90625, 7.03125);
((GeneralPath)shape).curveTo(8.652692, 8.38688, 8.5, 10.336574, 8.5, 12.5);
((GeneralPath)shape).lineTo(8.5, 42.5);
((GeneralPath)shape).lineTo(41.5, 42.5);
((GeneralPath)shape).lineTo(41.5, 12.5);
((GeneralPath)shape).curveTo(41.5, 10.336573, 41.34731, 8.38688, 41.09375, 7.03125);
((GeneralPath)shape).curveTo(40.979942, 6.422779, 40.835693, 5.984036, 40.71875, 5.6875);
((GeneralPath)shape).curveTo(40.629707, 6.153994, 40.224873, 6.4935346, 39.75, 6.5);
((GeneralPath)shape).lineTo(37.75, 6.5);
((GeneralPath)shape).curveTo(37.35759, 6.4970217, 37.00318, 6.2648244, 36.84375, 5.90625);
((GeneralPath)shape).lineTo(36.65625, 5.5);
((GeneralPath)shape).lineTo(13.3125, 5.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_4;
g.setTransform(defaultTransform__0_0_0_4);
g.setClip(clip__0_0_0_4);
float alpha__0_0_0_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_5 = g.getClip();
AffineTransform defaultTransform__0_0_0_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_5 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(12.75, 42.236083984375), new Point2D.Double(12.75, 39.04581069946289), new float[] {0.0f,1.0f}, new Color[] {new Color(186, 189, 182, 255),new Color(238, 238, 236, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(5.5, 37.5);
((GeneralPath)shape).curveTo(5.5, 40.824013, 6.0, 43.5, 7.5, 43.5);
((GeneralPath)shape).lineTo(42.5, 43.5);
((GeneralPath)shape).curveTo(44.0, 43.5, 44.5, 40.824013, 44.5, 37.5);
((GeneralPath)shape).lineTo(5.5, 37.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(7.5, 39.5);
((GeneralPath)shape).lineTo(9.5, 39.5);
((GeneralPath)shape).curveTo(9.472526, 40.53082, 9.585048, 41.10018, 9.75, 41.5);
((GeneralPath)shape).lineTo(7.75, 41.5);
((GeneralPath)shape).curveTo(7.5686812, 41.12729, 7.5356345, 40.51289, 7.5, 39.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(40.5, 39.5);
((GeneralPath)shape).lineTo(42.5, 39.5);
((GeneralPath)shape).curveTo(42.464363, 40.51289, 42.431316, 41.12729, 42.25, 41.5);
((GeneralPath)shape).lineTo(40.25, 41.5);
((GeneralPath)shape).curveTo(40.41495, 41.10018, 40.527473, 40.53082, 40.5, 39.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(136, 138, 133, 255);
stroke = new BasicStroke(1.0000008f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(5.5, 37.5);
((GeneralPath)shape).curveTo(5.5, 40.824013, 6.0, 43.5, 7.5, 43.5);
((GeneralPath)shape).lineTo(42.5, 43.5);
((GeneralPath)shape).curveTo(44.0, 43.5, 44.5, 40.824013, 44.5, 37.5);
((GeneralPath)shape).lineTo(5.5, 37.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(7.5, 39.5);
((GeneralPath)shape).lineTo(9.5, 39.5);
((GeneralPath)shape).curveTo(9.472526, 40.53082, 9.585048, 41.10018, 9.75, 41.5);
((GeneralPath)shape).lineTo(7.75, 41.5);
((GeneralPath)shape).curveTo(7.5686812, 41.12729, 7.5356345, 40.51289, 7.5, 39.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(40.5, 39.5);
((GeneralPath)shape).lineTo(42.5, 39.5);
((GeneralPath)shape).curveTo(42.464363, 40.51289, 42.431316, 41.12729, 42.25, 41.5);
((GeneralPath)shape).lineTo(40.25, 41.5);
((GeneralPath)shape).curveTo(40.41495, 41.10018, 40.527473, 40.53082, 40.5, 39.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_5;
g.setTransform(defaultTransform__0_0_0_5);
g.setClip(clip__0_0_0_5);
float alpha__0_0_0_6 = origAlpha;
origAlpha = origAlpha * 0.1f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_6 = g.getClip();
AffineTransform defaultTransform__0_0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_6 is ShapeNode
paint = new Color(136, 138, 133, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(6.125, 40.0);
((GeneralPath)shape).curveTo(6.183456, 40.609367, 6.2778854, 41.148273, 6.40625, 41.59375);
((GeneralPath)shape).curveTo(6.450931, 41.748814, 6.5091805, 41.865986, 6.5625, 42.0);
((GeneralPath)shape).lineTo(7.75, 42.0);
((GeneralPath)shape).curveTo(7.563049, 41.995434, 7.3942704, 41.886932, 7.3125, 41.71875);
((GeneralPath)shape).curveTo(7.09567, 41.273045, 7.0607805, 40.72197, 7.03125, 40.0);
((GeneralPath)shape).lineTo(6.125, 40.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(10.0, 40.0);
((GeneralPath)shape).curveTo(10.018193, 40.6601, 10.112137, 41.054085, 10.21875, 41.3125);
((GeneralPath)shape).curveTo(10.281429, 41.467487, 10.250429, 41.64312, 10.15625, 41.78125);
((GeneralPath)shape).curveTo(10.062071, 41.91938, 9.917172, 42.00173, 9.75, 42.0);
((GeneralPath)shape).lineTo(40.25, 42.0);
((GeneralPath)shape).curveTo(40.08283, 42.00173, 39.937927, 41.91938, 39.84375, 41.78125);
((GeneralPath)shape).curveTo(39.749573, 41.64312, 39.71857, 41.467487, 39.78125, 41.3125);
((GeneralPath)shape).curveTo(39.887863, 41.054085, 39.981808, 40.6601, 40.0, 40.0);
((GeneralPath)shape).lineTo(10.0, 40.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(42.96875, 40.0);
((GeneralPath)shape).curveTo(42.93922, 40.72197, 42.904327, 41.273045, 42.6875, 41.71875);
((GeneralPath)shape).curveTo(42.60573, 41.886932, 42.43695, 41.995434, 42.25, 42.0);
((GeneralPath)shape).lineTo(43.4375, 42.0);
((GeneralPath)shape).curveTo(43.490818, 41.865986, 43.54907, 41.74881, 43.59375, 41.59375);
((GeneralPath)shape).curveTo(43.722115, 41.148273, 43.816544, 40.609367, 43.875, 40.0);
((GeneralPath)shape).lineTo(42.96875, 40.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_6;
g.setTransform(defaultTransform__0_0_0_6);
g.setClip(clip__0_0_0_6);
float alpha__0_0_0_7 = origAlpha;
origAlpha = origAlpha * 0.8f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_7 = g.getClip();
AffineTransform defaultTransform__0_0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.005094051361084f, 0.0f, 0.0f, 1.0f, -0.1273369938135147f, 0.0f));
// _0_0_0_7 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(13.682637214660645, 39.49958419799805), new Point2D.Double(13.682637214660645, 43.89316177368164), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.997463f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(6.59375, 38.5);
((GeneralPath)shape).curveTo(6.644105, 39.625164, 6.7083464, 40.781944, 6.90625, 41.46875);
((GeneralPath)shape).curveTo(7.038494, 41.92769, 7.194645, 42.236572, 7.3125, 42.375);
((GeneralPath)shape).curveTo(7.430355, 42.513428, 7.420658, 42.5, 7.5, 42.5);
((GeneralPath)shape).lineTo(42.5, 42.5);
((GeneralPath)shape).curveTo(42.57934, 42.5, 42.569645, 42.513428, 42.6875, 42.375);
((GeneralPath)shape).curveTo(42.805355, 42.236572, 42.961506, 41.92769, 43.09375, 41.46875);
((GeneralPath)shape).curveTo(43.291653, 40.781944, 43.355896, 39.625164, 43.40625, 38.5);
((GeneralPath)shape).lineTo(6.59375, 38.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_7;
g.setTransform(defaultTransform__0_0_0_7);
g.setClip(clip__0_0_0_7);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
float alpha__0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -9.21660041809082f, 47.4345817565918f));
// _0_0_1 is CompositeGraphicsNode
origAlpha = alpha__0_0_1;
g.setTransform(defaultTransform__0_0_1);
g.setClip(clip__0_0_1);
float alpha__0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(0.7603266835212708f, 0.0f, 0.0f, 0.7207438349723816f, 12.58350944519043f, 14.12503719329834f));
// _0_0_2 is CompositeGraphicsNode
float alpha__0_0_2_0 = origAlpha;
origAlpha = origAlpha * 0.47368422f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_0 = g.getClip();
AffineTransform defaultTransform__0_0_2_0 = g.getTransform();
g.transform(new AffineTransform(1.4402250051498413f, 0.0f, 0.0f, 0.41901400685310364f, -8.581572532653809f, 29.820430755615234f));
// _0_0_2_0 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(22.571428298950195, 30.85714340209961), 15.571428f, new Point2D.Double(22.571428298950195, 30.85714340209961), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.6513760089874268f, 0.0f, 10.757539749145508f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(38.142857, 30.857143);
((GeneralPath)shape).curveTo(38.142857, 36.45889, 31.171291, 41.0, 22.571426, 41.0);
((GeneralPath)shape).curveTo(13.971566, 41.0, 7.0, 36.45889, 7.0, 30.857143);
((GeneralPath)shape).curveTo(7.0, 25.255398, 13.971566, 20.714287, 22.571428, 20.714287);
((GeneralPath)shape).curveTo(31.171291, 20.714287, 38.142857, 25.255398, 38.142857, 30.857143);
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
paint = new LinearGradientPaint(new Point2D.Double(6.72681999206543, 32.16169738769531), new Point2D.Double(40.93812561035156, 32.16169738769531), new float[] {0.0f,0.21f,0.84f,1.0f}, new Color[] {new Color(234, 210, 0, 182),new Color(255, 236, 65, 255),new Color(226, 204, 0, 255),new Color(195, 175, 0, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.089877963066101f, 0.0f, -5.109789848327637f));
shape = new RoundRectangle2D.Double(6.5, 17.5, 34.993770599365234, 27.00927734375, 4.469950199127197, 4.514106750488281);
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(31.630468368530273, 41.79181671142578), new Point2D.Double(8.671363830566406, 25.79352378845215), new float[] {0.0f,1.0f}, new Color[] {new Color(125, 100, 0, 255),new Color(190, 151, 0, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.089877963066101f, 0.0f, -3.986898899078369f));
stroke = new BasicStroke(0.99999946f,1,1,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(6.5, 17.5, 34.993770599365234, 27.00927734375, 4.469950199127197, 4.514106750488281);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2_1;
g.setTransform(defaultTransform__0_0_2_1);
g.setClip(clip__0_0_2_1);
float alpha__0_0_2_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_2 = g.getClip();
AffineTransform defaultTransform__0_0_2_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(10.907268524169922, 25.002281188964844), new Point2D.Double(30.875446319580078, 36.127281188964844), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 153),new Color(255, 255, 255, 76)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9991120100021362f, 0.0f, 0.0f, 1.0958019495010376f, 0.036421410739421844f, -4.139732837677002f));
stroke = new BasicStroke(1.0000001f,1,1,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(7.5, 18.5, 33.00090408325195, 25.006881713867188, 2.577022075653076, 2.577022075653076);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2_2;
g.setTransform(defaultTransform__0_0_2_2);
g.setClip(clip__0_0_2_2);
float alpha__0_0_2_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_3 = g.getClip();
AffineTransform defaultTransform__0_0_2_3 = g.getTransform();
g.transform(new AffineTransform(1.0662909746170044f, 0.0f, 0.0f, 1.0f, -1.5909899473190308f, 0.0f));
// _0_0_2_3 is CompositeGraphicsNode
float alpha__0_0_2_3_0 = origAlpha;
origAlpha = origAlpha * 0.5f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_3_0 = g.getClip();
AffineTransform defaultTransform__0_0_2_3_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_3_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(27.8125, 22.73945426940918), new Point2D.Double(27.82421875, 26.024690628051758), new float[] {0.0f,1.0f}, new Color[] {new Color(137, 109, 0, 255),new Color(161, 128, 0, 68)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.2307690382003784f, 0.0f, 0.0f, 0.32409000396728516f, -5.538462162017822f, 14.897740364074707f));
shape = new Rectangle2D.Double(8.0, 23.0, 32.0, 0.9722709655761719);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_3_0;
g.setTransform(defaultTransform__0_0_2_3_0);
g.setClip(clip__0_0_2_3_0);
float alpha__0_0_2_3_1 = origAlpha;
origAlpha = origAlpha * 0.5f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_3_1 = g.getClip();
AffineTransform defaultTransform__0_0_2_3_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_3_1 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(27.8125, 22.73945426940918), new Point2D.Double(27.82421875, 26.024690628051758), new float[] {0.0f,1.0f}, new Color[] {new Color(137, 109, 0, 255),new Color(161, 128, 0, 68)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.2307690382003784f, 0.0f, 0.0f, 0.32409000396728516f, -5.538462162017822f, 16.89773941040039f));
shape = new Rectangle2D.Double(8.0, 25.0, 32.0, 0.9722709655761719);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_3_1;
g.setTransform(defaultTransform__0_0_2_3_1);
g.setClip(clip__0_0_2_3_1);
float alpha__0_0_2_3_2 = origAlpha;
origAlpha = origAlpha * 0.5f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_3_2 = g.getClip();
AffineTransform defaultTransform__0_0_2_3_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_3_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(27.8125, 22.73945426940918), new Point2D.Double(27.82421875, 26.024690628051758), new float[] {0.0f,1.0f}, new Color[] {new Color(137, 109, 0, 255),new Color(161, 128, 0, 68)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.2307690382003784f, 0.0f, 0.0f, 0.32409000396728516f, -5.538462162017822f, 18.89773941040039f));
shape = new Rectangle2D.Double(8.0, 27.0, 32.0, 0.9722709655761719);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_3_2;
g.setTransform(defaultTransform__0_0_2_3_2);
g.setClip(clip__0_0_2_3_2);
float alpha__0_0_2_3_3 = origAlpha;
origAlpha = origAlpha * 0.5f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_3_3 = g.getClip();
AffineTransform defaultTransform__0_0_2_3_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_3_3 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(27.8125, 22.73945426940918), new Point2D.Double(27.82421875, 26.024690628051758), new float[] {0.0f,1.0f}, new Color[] {new Color(137, 109, 0, 255),new Color(161, 128, 0, 68)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.2307690382003784f, 0.0f, 0.0f, 0.32409000396728516f, -5.538462162017822f, 20.89773941040039f));
shape = new Rectangle2D.Double(8.0, 29.0, 32.0, 0.9722709655761719);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_3_3;
g.setTransform(defaultTransform__0_0_2_3_3);
g.setClip(clip__0_0_2_3_3);
float alpha__0_0_2_3_4 = origAlpha;
origAlpha = origAlpha * 0.5f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_3_4 = g.getClip();
AffineTransform defaultTransform__0_0_2_3_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_3_4 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(27.8125, 22.73945426940918), new Point2D.Double(27.82421875, 26.024690628051758), new float[] {0.0f,1.0f}, new Color[] {new Color(137, 109, 0, 255),new Color(161, 128, 0, 68)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.2307690382003784f, 0.0f, 0.0f, 0.32409000396728516f, -5.538462162017822f, 22.89773941040039f));
shape = new Rectangle2D.Double(8.0, 31.0, 32.0, 0.9722709655761719);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_3_4;
g.setTransform(defaultTransform__0_0_2_3_4);
g.setClip(clip__0_0_2_3_4);
float alpha__0_0_2_3_5 = origAlpha;
origAlpha = origAlpha * 0.5f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_3_5 = g.getClip();
AffineTransform defaultTransform__0_0_2_3_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_3_5 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(27.8125, 22.73945426940918), new Point2D.Double(27.82421875, 26.024690628051758), new float[] {0.0f,1.0f}, new Color[] {new Color(137, 109, 0, 255),new Color(161, 128, 0, 68)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.2307690382003784f, 0.0f, 0.0f, 0.32409000396728516f, -5.538462162017822f, 24.89773941040039f));
shape = new Rectangle2D.Double(8.0, 33.0, 32.0, 0.9722709655761719);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_3_5;
g.setTransform(defaultTransform__0_0_2_3_5);
g.setClip(clip__0_0_2_3_5);
float alpha__0_0_2_3_6 = origAlpha;
origAlpha = origAlpha * 0.5f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_3_6 = g.getClip();
AffineTransform defaultTransform__0_0_2_3_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_3_6 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(27.8125, 22.73945426940918), new Point2D.Double(27.82421875, 26.024690628051758), new float[] {0.0f,1.0f}, new Color[] {new Color(137, 109, 0, 255),new Color(161, 128, 0, 68)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.2307690382003784f, 0.0f, 0.0f, 0.32409000396728516f, -5.538462162017822f, 26.89773941040039f));
shape = new Rectangle2D.Double(8.0, 35.0, 32.0, 0.9722709655761719);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_3_6;
g.setTransform(defaultTransform__0_0_2_3_6);
g.setClip(clip__0_0_2_3_6);
float alpha__0_0_2_3_7 = origAlpha;
origAlpha = origAlpha * 0.5f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_3_7 = g.getClip();
AffineTransform defaultTransform__0_0_2_3_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_3_7 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(27.8125, 22.73945426940918), new Point2D.Double(27.82421875, 26.024690628051758), new float[] {0.0f,1.0f}, new Color[] {new Color(137, 109, 0, 255),new Color(161, 128, 0, 68)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.2307690382003784f, 0.0f, 0.0f, 0.32409000396728516f, -5.538462162017822f, 28.89773941040039f));
shape = new Rectangle2D.Double(8.0, 37.0, 32.0, 0.9722709655761719);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_3_7;
g.setTransform(defaultTransform__0_0_2_3_7);
g.setClip(clip__0_0_2_3_7);
float alpha__0_0_2_3_8 = origAlpha;
origAlpha = origAlpha * 0.5f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_3_8 = g.getClip();
AffineTransform defaultTransform__0_0_2_3_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_3_8 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(27.8125, 22.73945426940918), new Point2D.Double(27.82421875, 26.024690628051758), new float[] {0.0f,1.0f}, new Color[] {new Color(137, 109, 0, 255),new Color(161, 128, 0, 68)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.2307690382003784f, 0.0f, 0.0f, 0.32409000396728516f, -5.538462162017822f, 30.89773941040039f));
shape = new Rectangle2D.Double(8.0, 39.0, 32.0, 0.9722709655761719);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_3_8;
g.setTransform(defaultTransform__0_0_2_3_8);
g.setClip(clip__0_0_2_3_8);
float alpha__0_0_2_3_9 = origAlpha;
origAlpha = origAlpha * 0.5f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_3_9 = g.getClip();
AffineTransform defaultTransform__0_0_2_3_9 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_3_9 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(27.8125, 22.73945426940918), new Point2D.Double(27.82421875, 26.024690628051758), new float[] {0.0f,1.0f}, new Color[] {new Color(137, 109, 0, 255),new Color(161, 128, 0, 68)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.2307690382003784f, 0.0f, 0.0f, 0.32409000396728516f, -5.538462162017822f, 32.89773941040039f));
shape = new Rectangle2D.Double(8.0, 41.0, 32.0, 0.9722709655761719);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_3_9;
g.setTransform(defaultTransform__0_0_2_3_9);
g.setClip(clip__0_0_2_3_9);
origAlpha = alpha__0_0_2_3;
g.setTransform(defaultTransform__0_0_2_3);
g.setClip(clip__0_0_2_3);
float alpha__0_0_2_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_4 = g.getClip();
AffineTransform defaultTransform__0_0_2_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_4 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(24.875, 21.0), new Point2D.Double(24.75, 17.0), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 236, 65, 255),new Color(195, 175, 0, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f));
shape = new RoundRectangle2D.Double(7.0, 18.0, 34.0, 4.0, 3.429290771484375, 3.3221452236175537);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_4;
g.setTransform(defaultTransform__0_0_2_4);
g.setClip(clip__0_0_2_4);
float alpha__0_0_2_5 = origAlpha;
origAlpha = origAlpha * 0.4065934f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_5 = g.getClip();
AffineTransform defaultTransform__0_0_2_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_5 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(12.845671653747559, 13.342373847961426), 17.0f, new Point2D.Double(12.845671653747559, 13.342373847961426), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.5310890674591064f, 0.0f, 0.0f, 2.860548973083496f, -19.66786003112793f, -16.339290618896484f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(39.022896, 21.954401);
((GeneralPath)shape).lineTo(8.978924, 21.954401);
((GeneralPath)shape).curveTo(7.4608216, 21.962502, 6.998229, 22.594587, 6.982604, 24.335226);
((GeneralPath)shape).lineTo(6.982604, 35.18088);
((GeneralPath)shape).curveTo(12.330784, 39.93756, 24.775284, 30.210882, 40.982605, 35.18088);
((GeneralPath)shape).lineTo(40.982605, 24.314186);
((GeneralPath)shape).curveTo(40.969994, 22.533916, 40.441757, 21.96814, 39.022896, 21.954401);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_5;
g.setTransform(defaultTransform__0_0_2_5);
g.setClip(clip__0_0_2_5);
float alpha__0_0_2_6 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_6 = g.getClip();
AffineTransform defaultTransform__0_0_2_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -2.0861629934643133E-7f, -0.987405002117157f));
// _0_0_2_6 is CompositeGraphicsNode
float alpha__0_0_2_6_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_6_0 = g.getClip();
AffineTransform defaultTransform__0_0_2_6_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_6_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(10.65084171295166, 2.913684129714966), new Point2D.Double(27.19227409362793, 17.47001075744629), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(202, 208, 198, 255),new Color(234, 236, 233, 255),new Color(197, 203, 192, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -1.9262789487838745f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(10.5, 20.234846);
((GeneralPath)shape).lineTo(10.5, 13.0);
((GeneralPath)shape).curveTo(10.5, 5.1298666, 15.897609, 1.3910066, 24.020027, 1.4892921);
((GeneralPath)shape).curveTo(32.18664, 1.5875777, 37.5, 5.037278, 37.5, 13.0);
((GeneralPath)shape).lineTo(37.5, 20.234846);
((GeneralPath)shape).curveTo(37.41605, 21.93843, 32.536613, 21.93215, 32.5, 20.234846);
((GeneralPath)shape).lineTo(32.5, 15.0);
((GeneralPath)shape).curveTo(32.5, 13.0, 33.138264, 6.5281467, 24.07724, 6.5281467);
((GeneralPath)shape).curveTo(14.953717, 6.5281467, 15.489988, 13.039885, 15.522679, 14.992025);
((GeneralPath)shape).lineTo(15.522679, 20.268524);
((GeneralPath)shape).curveTo(15.3125, 21.859846, 10.5, 21.797346, 10.5, 20.234846);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(19.25061798095703, 9.66357707977295), new Point2D.Double(16.198251724243164, 6.039654731750488), new float[] {0.0f,1.0f}, new Color[] {new Color(111, 113, 109, 255),new Color(158, 160, 156, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -1.9262789487838745f));
stroke = new BasicStroke(0.9999996f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(10.5, 20.234846);
((GeneralPath)shape).lineTo(10.5, 13.0);
((GeneralPath)shape).curveTo(10.5, 5.1298666, 15.897609, 1.3910066, 24.020027, 1.4892921);
((GeneralPath)shape).curveTo(32.18664, 1.5875777, 37.5, 5.037278, 37.5, 13.0);
((GeneralPath)shape).lineTo(37.5, 20.234846);
((GeneralPath)shape).curveTo(37.41605, 21.93843, 32.536613, 21.93215, 32.5, 20.234846);
((GeneralPath)shape).lineTo(32.5, 15.0);
((GeneralPath)shape).curveTo(32.5, 13.0, 33.138264, 6.5281467, 24.07724, 6.5281467);
((GeneralPath)shape).curveTo(14.953717, 6.5281467, 15.489988, 13.039885, 15.522679, 14.992025);
((GeneralPath)shape).lineTo(15.522679, 20.268524);
((GeneralPath)shape).curveTo(15.3125, 21.859846, 10.5, 21.797346, 10.5, 20.234846);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2_6_0;
g.setTransform(defaultTransform__0_0_2_6_0);
g.setClip(clip__0_0_2_6_0);
float alpha__0_0_2_6_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_6_1 = g.getClip();
AffineTransform defaultTransform__0_0_2_6_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_6_1 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(12.184196472167969, 8.546343803405762), 3.6037734f, new Point2D.Double(12.184196472167969, 8.546343803405762), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 126)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.44134199619293213f, 3.7869648933410645f, -3.285907030105591f, -0.38293901085853577f, 47.91817855834961f, -38.448299407958984f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.496849, 13.160018);
((GeneralPath)shape).curveTo(11.745273, 8.76218, 12.91305, 4.7080564, 18.456118, 3.198782);
((GeneralPath)shape).curveTo(20.036661, 5.0232134, 13.557817, 5.119544, 13.458448, 11.48175);
((GeneralPath)shape).curveTo(13.458448, 11.48175, 13.480108, 19.46426, 13.480108, 19.46426);
((GeneralPath)shape).curveTo(13.245274, 20.052649, 11.559349, 20.070826, 11.496849, 19.41457);
((GeneralPath)shape).lineTo(11.496849, 13.160013);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_6_1;
g.setTransform(defaultTransform__0_0_2_6_1);
g.setClip(clip__0_0_2_6_1);
float alpha__0_0_2_6_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_6_2 = g.getClip();
AffineTransform defaultTransform__0_0_2_6_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_6_2 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(30.996042251586914, 13.15640926361084), 1.000184f, new Point2D.Double(30.996042251586914, 13.15640926361084), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 0),new Color(255, 255, 255, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-5.316169904290291E-7f, 14.968979835510254f, -8.276676177978516f, 3.9688320248387754E-4f, 143.8948974609375f, -454.2723083496094f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(33.500366, 9.7120695);
((GeneralPath)shape).lineTo(35.500366, 9.7120695);
((GeneralPath)shape).lineTo(35.500366, 19.347483);
((GeneralPath)shape).curveTo(35.437866, 20.472483, 33.469116, 19.878733, 33.500366, 19.347483);
((GeneralPath)shape).lineTo(33.500366, 9.71207);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_6_2;
g.setTransform(defaultTransform__0_0_2_6_2);
g.setClip(clip__0_0_2_6_2);
origAlpha = alpha__0_0_2_6;
g.setTransform(defaultTransform__0_0_2_6);
g.setClip(clip__0_0_2_6);
float alpha__0_0_2_7 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_7 = g.getClip();
AffineTransform defaultTransform__0_0_2_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_7 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(21.941509246826172, 21.55086898803711), new Point2D.Double(21.941509246826172, 18.037588119506836), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.9519230127334595f, 0.0f, 1.9759629964828491f));
stroke = new BasicStroke(1.0f,1,1,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(7.5, 18.634618759155273, 33.0, 2.855769634246826, 2.400388479232788, 2.193695306777954);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2_7;
g.setTransform(defaultTransform__0_0_2_7);
g.setClip(clip__0_0_2_7);
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
        return 4;
    }

	/**
	 * Returns the width of the bounding box of the original SVG image.
	 * 
	 * @return The width of the bounding box of the original SVG image.
	 */
	public static int getOrigWidth() {
		return 48;
	}

	/**
	 * Returns the height of the bounding box of the original SVG image.
	 * 
	 * @return The height of the bounding box of the original SVG image.
	 */
	public static int getOrigHeight() {
		return 48;
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
	public ScrollLockIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public ScrollLockIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public ScrollLockIcon(int width, int height) {
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

