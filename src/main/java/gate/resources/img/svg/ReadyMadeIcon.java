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
public class ReadyMadeIcon implements
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
g.transform(new AffineTransform(1.0810799598693848f, 0.0f, 0.0f, 1.0810799598693848f, -1.4054018259048462f, -2.648624897003174f));
// _0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0 = origAlpha;
origAlpha = origAlpha * 0.4f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0000009536743164f, 0.0f, 0.0f, 0.8181840181350708f, -3.376530912646558E-6f, 7.363558769226074f));
// _0_0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_0_0 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(5.0, 41.5), 5.0f, new Point2D.Double(5.0, 41.5), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9900169968605042f, 0.0f, 0.0f, 1.100000023841858f, 32.11470031738281f, -5.150000095367432f));
shape = new Rectangle2D.Double(37.064781188964844, 35.0, 4.935218334197998, 11.0);
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
g.transform(new AffineTransform(-1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f));
// _0_0_0_0_1 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(5.0, 41.5), 5.0f, new Point2D.Double(5.0, 41.5), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9900100231170654f, 0.0f, 0.0f, 1.100000023841858f, -14.88523006439209f, -86.1500015258789f));
shape = new Rectangle2D.Double(-9.93518352508545, -46.0, 4.935183525085449, 11.0);
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
paint = new LinearGradientPaint(new Point2D.Double(17.55419158935547, 46.000274658203125), new Point2D.Double(17.55419158935547, 34.999717712402344), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(0, 0, 0, 0),new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.1795480251312256f, 0.0f, 0.0f, 1.0f, -4.219388961791992f, 0.0f));
shape = new Rectangle2D.Double(9.93518352508545, 35.0, 27.12959861755371, 11.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_0_2;
g.setTransform(defaultTransform__0_0_0_0_2);
g.setClip(clip__0_0_0_0_2);
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
paint = new LinearGradientPaint(new Point2D.Double(24.99049949645996, 49.42409896850586), new Point2D.Double(23.451570510864258, 14.3825101852417), new float[] {0.0f,1.0f}, new Color[] {new Color(234, 186, 111, 255),new Color(185, 122, 27, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.32990300655365f, 0.0f, -8.664409637451172f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.76152, 11.650434);
((GeneralPath)shape).lineTo(23.0, 10.962934);
((GeneralPath)shape).lineTo(23.03125, 11.650434);
((GeneralPath)shape).lineTo(34.76662, 11.650434);
((GeneralPath)shape).curveTo(36.109592, 11.650434, 36.940754, 12.155855, 37.503254, 13.544878);
((GeneralPath)shape).lineTo(39.440754, 19.375);
((GeneralPath)shape).lineTo(39.440754, 39.993874);
((GeneralPath)shape).curveTo(39.440754, 41.320396, 38.35959, 42.388317, 37.01662, 42.388317);
((GeneralPath)shape).lineTo(9.88652, 42.388317);
((GeneralPath)shape).curveTo(8.54355, 42.388317, 7.462385, 41.320396, 7.462385, 39.993874);
((GeneralPath)shape).lineTo(7.462385, 19.374998);
((GeneralPath)shape).lineTo(9.337385, 13.419876);
((GeneralPath)shape).curveTo(9.712385, 12.343354, 10.41855, 11.650433, 11.76152, 11.650433);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(160, 103, 12, 255);
stroke = new BasicStroke(1.0000008f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.76152, 11.650434);
((GeneralPath)shape).lineTo(23.0, 10.962934);
((GeneralPath)shape).lineTo(23.03125, 11.650434);
((GeneralPath)shape).lineTo(34.76662, 11.650434);
((GeneralPath)shape).curveTo(36.109592, 11.650434, 36.940754, 12.155855, 37.503254, 13.544878);
((GeneralPath)shape).lineTo(39.440754, 19.375);
((GeneralPath)shape).lineTo(39.440754, 39.993874);
((GeneralPath)shape).curveTo(39.440754, 41.320396, 38.35959, 42.388317, 37.01662, 42.388317);
((GeneralPath)shape).lineTo(9.88652, 42.388317);
((GeneralPath)shape).curveTo(8.54355, 42.388317, 7.462385, 41.320396, 7.462385, 39.993874);
((GeneralPath)shape).lineTo(7.462385, 19.374998);
((GeneralPath)shape).lineTo(9.337385, 13.419876);
((GeneralPath)shape).curveTo(9.712385, 12.343354, 10.41855, 11.650433, 11.76152, 11.650433);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_1;
g.setTransform(defaultTransform__0_0_0_1);
g.setClip(clip__0_0_0_1);
float alpha__0_0_0_2 = origAlpha;
origAlpha = origAlpha * 0.50549453f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(23.451576232910156, 30.554906845092773), new Point2D.Double(43.006629943847656, 45.934478759765625), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.000001f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(12.492646, 12.612816);
((GeneralPath)shape).lineTo(34.05896, 12.612816);
((GeneralPath)shape).curveTo(35.31794, 12.612816, 36.09712, 13.086589, 36.62444, 14.388632);
((GeneralPath)shape).lineTo(38.440765, 19.853678);
((GeneralPath)shape).lineTo(38.440765, 39.55642);
((GeneralPath)shape).curveTo(38.440765, 40.799877, 37.80222, 41.425926, 36.54324, 41.425926);
((GeneralPath)shape).lineTo(10.234909, 41.425926);
((GeneralPath)shape).curveTo(8.97593, 41.425926, 8.462384, 40.737377, 8.462384, 39.49392);
((GeneralPath)shape).lineTo(8.462384, 19.853678);
((GeneralPath)shape).lineTo(10.220118, 14.27146);
((GeneralPath)shape).curveTo(10.571665, 13.262347, 11.233664, 12.612816, 12.492642, 12.612816);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_2;
g.setTransform(defaultTransform__0_0_0_2);
g.setClip(clip__0_0_0_2);
float alpha__0_0_0_3 = origAlpha;
origAlpha = origAlpha * 0.50549453f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_3 = g.getClip();
AffineTransform defaultTransform__0_0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_3 is ShapeNode
paint = new Color(0, 0, 0, 193);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(8.375, 19.875);
((GeneralPath)shape).lineTo(23.0625, 20.25);
((GeneralPath)shape).lineTo(23.02467, 15.562832);
((GeneralPath)shape).curveTo(23.02467, 15.562832, 29.583738, 15.406064, 29.583738, 15.406064);
((GeneralPath)shape).curveTo(29.583738, 15.406064, 23.018091, 15.187889, 23.018091, 15.187889);
((GeneralPath)shape).lineTo(23.0, 13.125);
((GeneralPath)shape).lineTo(22.710804, 13.093243);
((GeneralPath)shape).lineTo(22.625, 19.0);
((GeneralPath)shape).lineTo(8.375, 19.875);
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
paint = new Color(245, 221, 184, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(8.5, 19.8125);
((GeneralPath)shape).lineTo(22.625, 19.03125);
((GeneralPath)shape).lineTo(22.75, 12.125);
((GeneralPath)shape).lineTo(22.28125, 18.53125);
((GeneralPath)shape).lineTo(9.3125, 19.4375);
((GeneralPath)shape).lineTo(8.5, 19.8125);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = new Color(220, 189, 142, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(22.3125, 18.5625);
((GeneralPath)shape).lineTo(8.0, 19.6875);
((GeneralPath)shape).lineTo(9.875, 13.4375);
((GeneralPath)shape).curveTo(10.374807, 12.447628, 11.271594, 12.053224, 12.4375, 12.0625);
((GeneralPath)shape).lineTo(22.78125, 11.4375);
((GeneralPath)shape).lineTo(22.3125, 18.5625);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_5;
g.setTransform(defaultTransform__0_0_0_5);
g.setClip(clip__0_0_0_5);
float alpha__0_0_0_6 = origAlpha;
origAlpha = origAlpha * 0.42857146f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_6 = g.getClip();
AffineTransform defaultTransform__0_0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_6 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(35.1875, 17.5), new Point2D.Double(26.5625, 17.4375), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(23.0, 13.125);
((GeneralPath)shape).lineTo(23.0, 19.0625);
((GeneralPath)shape).lineTo(37.6875, 19.125);
((GeneralPath)shape).lineTo(35.46462, 13.195313);
((GeneralPath)shape).lineTo(23.0, 13.125);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_6;
g.setTransform(defaultTransform__0_0_0_6);
g.setClip(clip__0_0_0_6);
float alpha__0_0_0_7 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_7 = g.getClip();
AffineTransform defaultTransform__0_0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_7 is ShapeNode
paint = new Color(198, 139, 49, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(39.3125, 19.375);
((GeneralPath)shape).lineTo(24.75, 12.625);
((GeneralPath)shape).lineTo(24.5, 4.9375);
((GeneralPath)shape).lineTo(36.07369, 11.644276);
((GeneralPath)shape).curveTo(36.50445, 11.959366, 36.97686, 12.399526, 37.25, 12.9375);
((GeneralPath)shape).lineTo(39.3125, 19.375);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(160, 103, 12, 255);
stroke = new BasicStroke(1.0f,0,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(39.3125, 19.375);
((GeneralPath)shape).lineTo(24.75, 12.625);
((GeneralPath)shape).lineTo(24.5, 4.9375);
((GeneralPath)shape).lineTo(36.07369, 11.644276);
((GeneralPath)shape).curveTo(36.50445, 11.959366, 36.97686, 12.399526, 37.25, 12.9375);
((GeneralPath)shape).lineTo(39.3125, 19.375);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_7;
g.setTransform(defaultTransform__0_0_0_7);
g.setClip(clip__0_0_0_7);
float alpha__0_0_0_8 = origAlpha;
origAlpha = origAlpha * 0.23076926f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_8 = g.getClip();
AffineTransform defaultTransform__0_0_0_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_8 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(28.0625, 19.0), new Point2D.Double(23.9375, 19.0), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(23.0, 19.0);
((GeneralPath)shape).lineTo(23.0, 20.0);
((GeneralPath)shape).lineTo(38.0, 20.0);
((GeneralPath)shape).lineTo(37.5625, 19.0);
((GeneralPath)shape).lineTo(23.0, 19.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_8;
g.setTransform(defaultTransform__0_0_0_8);
g.setClip(clip__0_0_0_8);
float alpha__0_0_0_9 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_9 = g.getClip();
AffineTransform defaultTransform__0_0_0_9 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_9 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(23.157747268676758, 7.142486572265625), new Point2D.Double(30.007844924926758, 11.473516464233398), new float[] {0.0f,1.0f}, new Color[] {new Color(252, 243, 230, 255),new Color(252, 243, 230, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(25.19068, 12.246913);
((GeneralPath)shape).lineTo(25.013903, 5.838758);
((GeneralPath)shape).lineTo(35.001785, 11.628195);
((GeneralPath)shape).lineTo(25.500038, 6.413282);
((GeneralPath)shape).lineTo(25.676815, 11.937553);
((GeneralPath)shape).lineTo(29.168156, 14.103068);
((GeneralPath)shape).lineTo(25.19068, 12.246912);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_9;
g.setTransform(defaultTransform__0_0_0_9);
g.setClip(clip__0_0_0_9);
float alpha__0_0_0_10 = origAlpha;
origAlpha = origAlpha * 0.10989009f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_10 = g.getClip();
AffineTransform defaultTransform__0_0_0_10 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_10 is ShapeNode
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(23.0, 19.0);
((GeneralPath)shape).lineTo(22.9375, 15.5625);
((GeneralPath)shape).lineTo(29.8125, 15.4375);
((GeneralPath)shape).lineTo(30.1875, 15.6875);
((GeneralPath)shape).lineTo(23.1875, 15.75);
((GeneralPath)shape).lineTo(23.0, 19.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_10;
g.setTransform(defaultTransform__0_0_0_10);
g.setClip(clip__0_0_0_10);
float alpha__0_0_0_11 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_11 = g.getClip();
AffineTransform defaultTransform__0_0_0_11 = g.getTransform();
g.transform(new AffineTransform(0.288928747177124f, 0.0f, 0.0f, 0.288928747177124f, 14.42214298248291f, 21.3856201171875f));
// _0_0_0_11 is CompositeGraphicsNode
float alpha__0_0_0_11_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_11_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_11_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -1.3457649946212769f, -3.6447529792785645f));
// _0_0_0_11_0 is CompositeGraphicsNode
float alpha__0_0_0_11_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_11_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_11_0_0 = g.getTransform();
g.transform(new AffineTransform(0.5496010184288025f, 0.7062069773674011f, -0.6977810263633728f, 0.5562379956245422f, 26.523630142211914f, -19.788820266723633f));
// _0_0_0_11_0_0 is ShapeNode
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
origAlpha = alpha__0_0_0_11_0_0;
g.setTransform(defaultTransform__0_0_0_11_0_0);
g.setClip(clip__0_0_0_11_0_0);
float alpha__0_0_0_11_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_11_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_11_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_11_0_1 is ShapeNode
paint = new Color(255, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.09375, 4.59375);
((GeneralPath)shape).curveTo(13.244158, 5.781618, 21.069134, 13.490205, 17.96875, 16.0625);
((GeneralPath)shape).curveTo(14.868366, 18.634794, 8.760565, 9.471451, 6.875, 13.03125);
((GeneralPath)shape).curveTo(4.9894347, 16.59105, 16.003635, 16.488544, 15.625, 20.5);
((GeneralPath)shape).curveTo(15.246366, 24.511457, 4.4121246, 22.367374, 5.59375, 26.21875);
((GeneralPath)shape).curveTo(6.7753754, 30.070126, 14.528686, 22.238714, 17.09375, 25.34375);
((GeneralPath)shape).curveTo(19.658813, 28.448786, 10.503517, 34.54975, 14.0625, 36.4375);
((GeneralPath)shape).curveTo(17.621483, 38.325253, 17.52045, 27.313276, 21.53125, 27.6875);
((GeneralPath)shape).curveTo(25.542051, 28.061724, 23.39731, 38.86644, 27.25, 37.6875);
((GeneralPath)shape).curveTo(31.102692, 36.50856, 23.274061, 28.788654, 26.375, 26.21875);
((GeneralPath)shape).curveTo(29.475939, 23.648846, 35.584667, 32.81367, 37.46875, 29.25);
((GeneralPath)shape).curveTo(39.352833, 25.686329, 28.339485, 25.762918, 28.71875, 21.75);
((GeneralPath)shape).curveTo(29.098017, 17.737082, 39.876114, 19.880157, 38.6875, 16.03125);
((GeneralPath)shape).curveTo(37.498886, 12.182344, 29.817188, 20.04152, 27.25, 16.9375);
((GeneralPath)shape).curveTo(24.682814, 13.833482, 33.812073, 7.693837, 30.25, 5.8125);
((GeneralPath)shape).curveTo(26.687927, 3.9311628, 26.792248, 14.944821, 22.78125, 14.5625);
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
((GeneralPath)shape).curveTo(4.9894347, 16.59105, 16.003635, 16.488544, 15.625, 20.5);
((GeneralPath)shape).curveTo(15.246366, 24.511457, 4.4121246, 22.367374, 5.59375, 26.21875);
((GeneralPath)shape).curveTo(6.7753754, 30.070126, 14.528686, 22.238714, 17.09375, 25.34375);
((GeneralPath)shape).curveTo(19.658813, 28.448786, 10.503517, 34.54975, 14.0625, 36.4375);
((GeneralPath)shape).curveTo(17.621483, 38.325253, 17.52045, 27.313276, 21.53125, 27.6875);
((GeneralPath)shape).curveTo(25.542051, 28.061724, 23.39731, 38.86644, 27.25, 37.6875);
((GeneralPath)shape).curveTo(31.102692, 36.50856, 23.274061, 28.788654, 26.375, 26.21875);
((GeneralPath)shape).curveTo(29.475939, 23.648846, 35.584667, 32.81367, 37.46875, 29.25);
((GeneralPath)shape).curveTo(39.352833, 25.686329, 28.339485, 25.762918, 28.71875, 21.75);
((GeneralPath)shape).curveTo(29.098017, 17.737082, 39.876114, 19.880157, 38.6875, 16.03125);
((GeneralPath)shape).curveTo(37.498886, 12.182344, 29.817188, 20.04152, 27.25, 16.9375);
((GeneralPath)shape).curveTo(24.682814, 13.833482, 33.812073, 7.693837, 30.25, 5.8125);
((GeneralPath)shape).curveTo(26.687927, 3.9311628, 26.792248, 14.944821, 22.78125, 14.5625);
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
origAlpha = alpha__0_0_0_11_0_1;
g.setTransform(defaultTransform__0_0_0_11_0_1);
g.setClip(clip__0_0_0_11_0_1);
origAlpha = alpha__0_0_0_11_0;
g.setTransform(defaultTransform__0_0_0_11_0);
g.setClip(clip__0_0_0_11_0);
float alpha__0_0_0_11_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_11_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_11_1 = g.getTransform();
g.transform(new AffineTransform(0.9633870124816895f, 0.26811298727989197f, -0.26811298727989197f, 0.9633870124816895f, 30.81049919128418f, 3.225944995880127f));
// _0_0_0_11_1 is CompositeGraphicsNode
float alpha__0_0_0_11_1_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_11_1_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_11_1_0 = g.getTransform();
g.transform(new AffineTransform(0.5496010184288025f, 0.7062069773674011f, -0.6977810263633728f, 0.5562379956245422f, 26.523630142211914f, -19.788820266723633f));
// _0_0_0_11_1_0 is ShapeNode
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
origAlpha = alpha__0_0_0_11_1_0;
g.setTransform(defaultTransform__0_0_0_11_1_0);
g.setClip(clip__0_0_0_11_1_0);
float alpha__0_0_0_11_1_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_11_1_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_11_1_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_11_1_1 is ShapeNode
paint = new Color(255, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.09375, 4.59375);
((GeneralPath)shape).curveTo(13.244158, 5.781618, 21.069134, 13.490205, 17.96875, 16.0625);
((GeneralPath)shape).curveTo(14.868366, 18.634794, 8.760565, 9.471451, 6.875, 13.03125);
((GeneralPath)shape).curveTo(4.9894347, 16.59105, 16.003635, 16.488544, 15.625, 20.5);
((GeneralPath)shape).curveTo(15.246366, 24.511457, 4.4121246, 22.367374, 5.59375, 26.21875);
((GeneralPath)shape).curveTo(6.7753754, 30.070126, 14.528686, 22.238714, 17.09375, 25.34375);
((GeneralPath)shape).curveTo(19.658813, 28.448786, 10.503517, 34.54975, 14.0625, 36.4375);
((GeneralPath)shape).curveTo(17.621483, 38.325253, 17.52045, 27.313276, 21.53125, 27.6875);
((GeneralPath)shape).curveTo(25.542051, 28.061724, 23.39731, 38.86644, 27.25, 37.6875);
((GeneralPath)shape).curveTo(31.102692, 36.50856, 23.274061, 28.788654, 26.375, 26.21875);
((GeneralPath)shape).curveTo(29.475939, 23.648846, 35.584667, 32.81367, 37.46875, 29.25);
((GeneralPath)shape).curveTo(39.352833, 25.686329, 28.339485, 25.762918, 28.71875, 21.75);
((GeneralPath)shape).curveTo(29.098017, 17.737082, 39.876114, 19.880157, 38.6875, 16.03125);
((GeneralPath)shape).curveTo(37.498886, 12.182344, 29.817188, 20.04152, 27.25, 16.9375);
((GeneralPath)shape).curveTo(24.682814, 13.833482, 33.812073, 7.693837, 30.25, 5.8125);
((GeneralPath)shape).curveTo(26.687927, 3.9311628, 26.792248, 14.944821, 22.78125, 14.5625);
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
((GeneralPath)shape).curveTo(4.9894347, 16.59105, 16.003635, 16.488544, 15.625, 20.5);
((GeneralPath)shape).curveTo(15.246366, 24.511457, 4.4121246, 22.367374, 5.59375, 26.21875);
((GeneralPath)shape).curveTo(6.7753754, 30.070126, 14.528686, 22.238714, 17.09375, 25.34375);
((GeneralPath)shape).curveTo(19.658813, 28.448786, 10.503517, 34.54975, 14.0625, 36.4375);
((GeneralPath)shape).curveTo(17.621483, 38.325253, 17.52045, 27.313276, 21.53125, 27.6875);
((GeneralPath)shape).curveTo(25.542051, 28.061724, 23.39731, 38.86644, 27.25, 37.6875);
((GeneralPath)shape).curveTo(31.102692, 36.50856, 23.274061, 28.788654, 26.375, 26.21875);
((GeneralPath)shape).curveTo(29.475939, 23.648846, 35.584667, 32.81367, 37.46875, 29.25);
((GeneralPath)shape).curveTo(39.352833, 25.686329, 28.339485, 25.762918, 28.71875, 21.75);
((GeneralPath)shape).curveTo(29.098017, 17.737082, 39.876114, 19.880157, 38.6875, 16.03125);
((GeneralPath)shape).curveTo(37.498886, 12.182344, 29.817188, 20.04152, 27.25, 16.9375);
((GeneralPath)shape).curveTo(24.682814, 13.833482, 33.812073, 7.693837, 30.25, 5.8125);
((GeneralPath)shape).curveTo(26.687927, 3.9311628, 26.792248, 14.944821, 22.78125, 14.5625);
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
origAlpha = alpha__0_0_0_11_1_1;
g.setTransform(defaultTransform__0_0_0_11_1_1);
g.setClip(clip__0_0_0_11_1_1);
origAlpha = alpha__0_0_0_11_1;
g.setTransform(defaultTransform__0_0_0_11_1);
g.setClip(clip__0_0_0_11_1);
float alpha__0_0_0_11_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_11_2 = g.getClip();
AffineTransform defaultTransform__0_0_0_11_2 = g.getTransform();
g.transform(new AffineTransform(0.9525910019874573f, 0.30425500869750977f, -0.30425500869750977f, 0.9525910019874573f, 5.163568019866943f, 19.293319702148438f));
// _0_0_0_11_2 is CompositeGraphicsNode
float alpha__0_0_0_11_2_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_11_2_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_11_2_0 = g.getTransform();
g.transform(new AffineTransform(0.5496010184288025f, 0.7062069773674011f, -0.6977810263633728f, 0.5562379956245422f, 26.523630142211914f, -19.788820266723633f));
// _0_0_0_11_2_0 is ShapeNode
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
origAlpha = alpha__0_0_0_11_2_0;
g.setTransform(defaultTransform__0_0_0_11_2_0);
g.setClip(clip__0_0_0_11_2_0);
float alpha__0_0_0_11_2_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_11_2_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_11_2_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_11_2_1 is ShapeNode
paint = new Color(255, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.09375, 4.59375);
((GeneralPath)shape).curveTo(13.244158, 5.781618, 21.069134, 13.490205, 17.96875, 16.0625);
((GeneralPath)shape).curveTo(14.868366, 18.634794, 8.760565, 9.471451, 6.875, 13.03125);
((GeneralPath)shape).curveTo(4.9894347, 16.59105, 16.003635, 16.488544, 15.625, 20.5);
((GeneralPath)shape).curveTo(15.246366, 24.511457, 4.4121246, 22.367374, 5.59375, 26.21875);
((GeneralPath)shape).curveTo(6.7753754, 30.070126, 14.528686, 22.238714, 17.09375, 25.34375);
((GeneralPath)shape).curveTo(19.658813, 28.448786, 10.503517, 34.54975, 14.0625, 36.4375);
((GeneralPath)shape).curveTo(17.621483, 38.325253, 17.52045, 27.313276, 21.53125, 27.6875);
((GeneralPath)shape).curveTo(25.542051, 28.061724, 23.39731, 38.86644, 27.25, 37.6875);
((GeneralPath)shape).curveTo(31.102692, 36.50856, 23.274061, 28.788654, 26.375, 26.21875);
((GeneralPath)shape).curveTo(29.475939, 23.648846, 35.584667, 32.81367, 37.46875, 29.25);
((GeneralPath)shape).curveTo(39.352833, 25.686329, 28.339485, 25.762918, 28.71875, 21.75);
((GeneralPath)shape).curveTo(29.098017, 17.737082, 39.876114, 19.880157, 38.6875, 16.03125);
((GeneralPath)shape).curveTo(37.498886, 12.182344, 29.817188, 20.04152, 27.25, 16.9375);
((GeneralPath)shape).curveTo(24.682814, 13.833482, 33.812073, 7.693837, 30.25, 5.8125);
((GeneralPath)shape).curveTo(26.687927, 3.9311628, 26.792248, 14.944821, 22.78125, 14.5625);
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
((GeneralPath)shape).curveTo(4.9894347, 16.59105, 16.003635, 16.488544, 15.625, 20.5);
((GeneralPath)shape).curveTo(15.246366, 24.511457, 4.4121246, 22.367374, 5.59375, 26.21875);
((GeneralPath)shape).curveTo(6.7753754, 30.070126, 14.528686, 22.238714, 17.09375, 25.34375);
((GeneralPath)shape).curveTo(19.658813, 28.448786, 10.503517, 34.54975, 14.0625, 36.4375);
((GeneralPath)shape).curveTo(17.621483, 38.325253, 17.52045, 27.313276, 21.53125, 27.6875);
((GeneralPath)shape).curveTo(25.542051, 28.061724, 23.39731, 38.86644, 27.25, 37.6875);
((GeneralPath)shape).curveTo(31.102692, 36.50856, 23.274061, 28.788654, 26.375, 26.21875);
((GeneralPath)shape).curveTo(29.475939, 23.648846, 35.584667, 32.81367, 37.46875, 29.25);
((GeneralPath)shape).curveTo(39.352833, 25.686329, 28.339485, 25.762918, 28.71875, 21.75);
((GeneralPath)shape).curveTo(29.098017, 17.737082, 39.876114, 19.880157, 38.6875, 16.03125);
((GeneralPath)shape).curveTo(37.498886, 12.182344, 29.817188, 20.04152, 27.25, 16.9375);
((GeneralPath)shape).curveTo(24.682814, 13.833482, 33.812073, 7.693837, 30.25, 5.8125);
((GeneralPath)shape).curveTo(26.687927, 3.9311628, 26.792248, 14.944821, 22.78125, 14.5625);
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
origAlpha = alpha__0_0_0_11_2_1;
g.setTransform(defaultTransform__0_0_0_11_2_1);
g.setClip(clip__0_0_0_11_2_1);
origAlpha = alpha__0_0_0_11_2;
g.setTransform(defaultTransform__0_0_0_11_2);
g.setClip(clip__0_0_0_11_2);
origAlpha = alpha__0_0_0_11;
g.setTransform(defaultTransform__0_0_0_11);
g.setClip(clip__0_0_0_11);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
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
        return 4;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 3;
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
	public ReadyMadeIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public ReadyMadeIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public ReadyMadeIcon(int width, int height) {
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

