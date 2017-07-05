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
public class GazetteerIcon implements
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0 is ShapeNode
paint = new Color(9, 176, 15, 103);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(33.01564, 36.136364);
((GeneralPath)shape).lineTo(51.742912, 45.772728);
((GeneralPath)shape).curveTo(54.504368, 47.227272, 49.923187, 45.30316, 47.924732, 45.681816);
((GeneralPath)shape).curveTo(45.077576, 46.22128, 33.958916, 51.045452, 43.924732, 62.5);
((GeneralPath)shape).lineTo(8.652004, 45.590908);
((GeneralPath)shape).curveTo(6.4360046, 45.590908, 7.5271354, 34.87475, 11.197458, 31.5);
((GeneralPath)shape).curveTo(17.01564, 26.150364, 30.79964, 36.136364, 33.01564, 36.136364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(33.01564, 36.136364);
((GeneralPath)shape).lineTo(51.742912, 45.772728);
((GeneralPath)shape).curveTo(54.504368, 47.227272, 49.923187, 45.30316, 47.924732, 45.681816);
((GeneralPath)shape).curveTo(45.077576, 46.22128, 33.958916, 51.045452, 43.924732, 62.5);
((GeneralPath)shape).lineTo(8.652004, 45.590908);
((GeneralPath)shape).curveTo(6.4360046, 45.590908, 7.5271354, 34.87475, 11.197458, 31.5);
((GeneralPath)shape).curveTo(17.01564, 26.150364, 30.79964, 36.136364, 33.01564, 36.136364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
float alpha__0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.022387981414795f, 0.0f, 0.0f, 1.0025379657745361f, 31.43256950378418f, 21.83064079284668f));
// _0_0_1 is CompositeGraphicsNode
float alpha__0_0_1_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_0 = g.getClip();
AffineTransform defaultTransform__0_0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_0 is ShapeNode
paint = new Color(9, 176, 15, 103);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.181818, 33.0);
((GeneralPath)shape).curveTo(26.181818, 37.669315, 22.315186, 41.454544, 17.545454, 41.454544);
((GeneralPath)shape).curveTo(12.775722, 41.454544, 8.90909, 37.669315, 8.90909, 33.0);
((GeneralPath)shape).curveTo(8.90909, 28.330685, 12.775722, 24.545456, 17.545454, 24.545456);
((GeneralPath)shape).curveTo(22.315186, 24.545456, 26.181818, 28.330685, 26.181818, 33.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.181818, 33.0);
((GeneralPath)shape).curveTo(26.181818, 37.669315, 22.315186, 41.454544, 17.545454, 41.454544);
((GeneralPath)shape).curveTo(12.775722, 41.454544, 8.90909, 37.669315, 8.90909, 33.0);
((GeneralPath)shape).curveTo(8.90909, 28.330685, 12.775722, 24.545456, 17.545454, 24.545456);
((GeneralPath)shape).curveTo(22.315186, 24.545456, 26.181818, 28.330685, 26.181818, 33.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_1_0;
g.setTransform(defaultTransform__0_0_1_0);
g.setClip(clip__0_0_1_0);
float alpha__0_0_1_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_1 = g.getClip();
AffineTransform defaultTransform__0_0_1_1 = g.getTransform();
g.transform(new AffineTransform(0.7835819721221924f, 0.0f, 0.0f, 0.7842640280723572f, 4.092436790466309f, 7.141845226287842f));
// _0_0_1_1 is ShapeNode
paint = new Color(9, 176, 15, 103);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.181818, 33.0);
((GeneralPath)shape).curveTo(26.181818, 37.669315, 22.315186, 41.454544, 17.545454, 41.454544);
((GeneralPath)shape).curveTo(12.775722, 41.454544, 8.90909, 37.669315, 8.90909, 33.0);
((GeneralPath)shape).curveTo(8.90909, 28.330685, 12.775722, 24.545456, 17.545454, 24.545456);
((GeneralPath)shape).curveTo(22.315186, 24.545456, 26.181818, 28.330685, 26.181818, 33.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.181818, 33.0);
((GeneralPath)shape).curveTo(26.181818, 37.669315, 22.315186, 41.454544, 17.545454, 41.454544);
((GeneralPath)shape).curveTo(12.775722, 41.454544, 8.90909, 37.669315, 8.90909, 33.0);
((GeneralPath)shape).curveTo(8.90909, 28.330685, 12.775722, 24.545456, 17.545454, 24.545456);
((GeneralPath)shape).curveTo(22.315186, 24.545456, 26.181818, 28.330685, 26.181818, 33.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_1_1;
g.setTransform(defaultTransform__0_0_1_1);
g.setClip(clip__0_0_1_1);
float alpha__0_0_1_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_2 = g.getClip();
AffineTransform defaultTransform__0_0_1_2 = g.getTransform();
g.transform(new AffineTransform(0.5522390007972717f, 0.0f, 0.0f, 0.5152279734611511f, 8.346758842468262f, 16.62019920349121f));
// _0_0_1_2 is ShapeNode
paint = new Color(9, 176, 15, 103);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.181818, 33.0);
((GeneralPath)shape).curveTo(26.181818, 37.669315, 22.315186, 41.454544, 17.545454, 41.454544);
((GeneralPath)shape).curveTo(12.775722, 41.454544, 8.90909, 37.669315, 8.90909, 33.0);
((GeneralPath)shape).curveTo(8.90909, 28.330685, 12.775722, 24.545456, 17.545454, 24.545456);
((GeneralPath)shape).curveTo(22.315186, 24.545456, 26.181818, 28.330685, 26.181818, 33.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.181818, 33.0);
((GeneralPath)shape).curveTo(26.181818, 37.669315, 22.315186, 41.454544, 17.545454, 41.454544);
((GeneralPath)shape).curveTo(12.775722, 41.454544, 8.90909, 37.669315, 8.90909, 33.0);
((GeneralPath)shape).curveTo(8.90909, 28.330685, 12.775722, 24.545456, 17.545454, 24.545456);
((GeneralPath)shape).curveTo(22.315186, 24.545456, 26.181818, 28.330685, 26.181818, 33.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_1_2;
g.setTransform(defaultTransform__0_0_1_2);
g.setClip(clip__0_0_1_2);
origAlpha = alpha__0_0_1;
g.setTransform(defaultTransform__0_0_1);
g.setClip(clip__0_0_1);
float alpha__0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(7.545454025268555, 72.18104553222656), new Point2D.Double(37.643924713134766, 14.908315658569336), new float[] {0.0f,1.0f}, new Color[] {new Color(9, 176, 15, 255),new Color(9, 176, 15, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(12.570151, 29.280361);
((GeneralPath)shape).lineTo(19.316214, 25.510654);
((GeneralPath)shape).curveTo(32.305244, 14.353301, 23.107044, 0.25323445, 26.264399, 3.060446);
((GeneralPath)shape).lineTo(60.053017, 15.482296);
((GeneralPath)shape).curveTo(59.755825, 28.653143, 47.254307, 36.71087, 43.41231, 41.88502);
((GeneralPath)shape).curveTo(42.60481, 42.97252, 39.236183, 43.321293, 38.120796, 53.291096);
((GeneralPath)shape).curveTo(37.002983, 63.2826, 53.602695, 64.91215, 37.718067, 60.28676);
((GeneralPath)shape).lineTo(7.0203595, 46.410362);
((GeneralPath)shape).curveTo(3.8630052, 43.603153, 5.5811214, 31.16499, 12.570151, 29.280361);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.9999999f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(12.570151, 29.280361);
((GeneralPath)shape).lineTo(19.316214, 25.510654);
((GeneralPath)shape).curveTo(32.305244, 14.353301, 23.107044, 0.25323445, 26.264399, 3.060446);
((GeneralPath)shape).lineTo(60.053017, 15.482296);
((GeneralPath)shape).curveTo(59.755825, 28.653143, 47.254307, 36.71087, 43.41231, 41.88502);
((GeneralPath)shape).curveTo(42.60481, 42.97252, 39.236183, 43.321293, 38.120796, 53.291096);
((GeneralPath)shape).curveTo(37.002983, 63.2826, 53.602695, 64.91215, 37.718067, 60.28676);
((GeneralPath)shape).lineTo(7.0203595, 46.410362);
((GeneralPath)shape).curveTo(3.8630052, 43.603153, 5.5811214, 31.16499, 12.570151, 29.280361);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2;
g.setTransform(defaultTransform__0_0_2);
g.setClip(clip__0_0_2);
float alpha__0_0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3 = g.getClip();
AffineTransform defaultTransform__0_0_3 = g.getTransform();
g.transform(new AffineTransform(0.9023240208625793f, 0.3448449969291687f, -0.31498199701309204f, 0.987870991230011f, 0.0f, 0.0f));
// _0_0_3 is TextNode of 'Gazette'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(35.100815, 1.5398921);
((GeneralPath)shape).lineTo(32.512104, 1.5398921);
((GeneralPath)shape).quadTo(31.62266, 1.5398921, 31.041025, 0.9495186);
((GeneralPath)shape).quadTo(30.45939, 0.35914516, 30.45939, -0.5419511);
((GeneralPath)shape).quadTo(30.45939, -1.4372214, 31.035198, -1.9858415);
((GeneralPath)shape).quadTo(31.611008, -2.5344615, 32.512104, -2.5344615);
((GeneralPath)shape).lineTo(34.81534, -2.5344615);
((GeneralPath)shape).lineTo(34.81534, -1.825625);
((GeneralPath)shape).lineTo(32.512104, -1.825625);
((GeneralPath)shape).quadTo(31.927557, -1.825625, 31.548862, -1.4498445);
((GeneralPath)shape).quadTo(31.17017, -1.074064, 31.17017, -0.48174858);
((GeneralPath)shape).quadTo(31.17017, 0.10279881, 31.548862, 0.46595615);
((GeneralPath)shape).quadTo(31.927557, 0.82911354, 32.512104, 0.82911354);
((GeneralPath)shape).lineTo(34.391975, 0.82911354);
((GeneralPath)shape).lineTo(34.391975, -0.05838868);
((GeneralPath)shape).lineTo(32.459667, -0.05838868);
((GeneralPath)shape).lineTo(32.459667, -0.7089647);
((GeneralPath)shape).lineTo(35.100815, -0.7089647);
((GeneralPath)shape).lineTo(35.100815, 1.5398921);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(40.98444, 1.5398921);
((GeneralPath)shape).lineTo(40.07169, 1.5340661);
((GeneralPath)shape).lineTo(39.386158, 0.3941015);
((GeneralPath)shape).lineTo(37.45191, 0.3941015);
((GeneralPath)shape).lineTo(37.881092, -0.308909);
((GeneralPath)shape).lineTo(38.960857, -0.308909);
((GeneralPath)shape).lineTo(38.252018, -1.4857719);
((GeneralPath)shape).lineTo(36.41487, 1.5340661);
((GeneralPath)shape).lineTo(35.50212, 1.5340661);
((GeneralPath)shape).lineTo(37.71408, -2.1460578);
((GeneralPath)shape).quadTo(37.79759, -2.2897673, 37.947124, -2.4101725);
((GeneralPath)shape).quadTo(38.12579, -2.5461137, 38.269497, -2.5461137);
((GeneralPath)shape).quadTo(38.42486, -2.5461137, 38.591873, -2.4159985);
((GeneralPath)shape).quadTo(38.73558, -2.3014195, 38.824913, -2.1460578);
((GeneralPath)shape).lineTo(40.98444, 1.5398921);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(45.506092, 1.5398921);
((GeneralPath)shape).lineTo(41.699734, 1.5398921);
((GeneralPath)shape).quadTo(41.24142, 1.5398921, 41.24142, 1.2466474);
((GeneralPath)shape).quadTo(41.24142, 1.0621557, 41.46087, 0.8485337);
((GeneralPath)shape).lineTo(44.12726, -1.825625);
((GeneralPath)shape).lineTo(41.138493, -1.825625);
((GeneralPath)shape).lineTo(41.138493, -2.5344615);
((GeneralPath)shape).lineTo(44.886585, -2.5344615);
((GeneralPath)shape).quadTo(45.07108, -2.5344615, 45.201195, -2.456781);
((GeneralPath)shape).quadTo(45.356556, -2.367448, 45.356556, -2.1946084);
((GeneralPath)shape).quadTo(45.356556, -2.0159428, 45.17789, -1.8431032);
((GeneralPath)shape).lineTo(42.511497, 0.82911354);
((GeneralPath)shape).lineTo(45.506092, 0.82911354);
((GeneralPath)shape).lineTo(45.506092, 1.5398921);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(49.44998, -0.17296773);
((GeneralPath)shape).lineTo(47.123444, -0.17296773);
((GeneralPath)shape).lineTo(47.123444, -0.8818043);
((GeneralPath)shape).lineTo(49.44998, -0.8818043);
((GeneralPath)shape).lineTo(49.44998, -0.17296773);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(49.63447, 1.5398921);
((GeneralPath)shape).lineTo(46.115536, 1.5398921);
((GeneralPath)shape).lineTo(46.115536, -2.5344615);
((GeneralPath)shape).lineTo(49.63447, -2.5344615);
((GeneralPath)shape).lineTo(49.63447, -1.825625);
((GeneralPath)shape).lineTo(46.824375, -1.825625);
((GeneralPath)shape).lineTo(46.824375, 0.82911354);
((GeneralPath)shape).lineTo(49.63447, 0.82911354);
((GeneralPath)shape).lineTo(49.63447, 1.5398921);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(54.09721, -1.825625);
((GeneralPath)shape).lineTo(52.493103, -1.825625);
((GeneralPath)shape).lineTo(52.493103, 1.5398921);
((GeneralPath)shape).lineTo(51.78815, 1.5398921);
((GeneralPath)shape).lineTo(51.78815, -1.825625);
((GeneralPath)shape).lineTo(50.178215, -1.825625);
((GeneralPath)shape).lineTo(50.178215, -2.5344615);
((GeneralPath)shape).lineTo(54.09721, -2.5344615);
((GeneralPath)shape).lineTo(54.09721, -1.825625);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(58.255436, -1.825625);
((GeneralPath)shape).lineTo(56.65133, -1.825625);
((GeneralPath)shape).lineTo(56.65133, 1.5398921);
((GeneralPath)shape).lineTo(55.946377, 1.5398921);
((GeneralPath)shape).lineTo(55.946377, -1.825625);
((GeneralPath)shape).lineTo(54.33644, -1.825625);
((GeneralPath)shape).lineTo(54.33644, -2.5344615);
((GeneralPath)shape).lineTo(58.255436, -2.5344615);
((GeneralPath)shape).lineTo(58.255436, -1.825625);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(62.073807, -0.17296773);
((GeneralPath)shape).lineTo(59.74727, -0.17296773);
((GeneralPath)shape).lineTo(59.74727, -0.8818043);
((GeneralPath)shape).lineTo(62.073807, -0.8818043);
((GeneralPath)shape).lineTo(62.073807, -0.17296773);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(62.258297, 1.5398921);
((GeneralPath)shape).lineTo(58.73936, 1.5398921);
((GeneralPath)shape).lineTo(58.73936, -2.5344615);
((GeneralPath)shape).lineTo(62.258297, -2.5344615);
((GeneralPath)shape).lineTo(62.258297, -1.825625);
((GeneralPath)shape).lineTo(59.4482, -1.825625);
((GeneralPath)shape).lineTo(59.4482, 0.82911354);
((GeneralPath)shape).lineTo(62.258297, 0.82911354);
((GeneralPath)shape).lineTo(62.258297, 1.5398921);
((GeneralPath)shape).closePath();
paint = new Color(0, 0, 0, 255);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_3;
g.setTransform(defaultTransform__0_0_3);
g.setClip(clip__0_0_3);
float alpha__0_0_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_4 = g.getClip();
AffineTransform defaultTransform__0_0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_4 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(27.0, 18.0);
((GeneralPath)shape).curveTo(35.0, 22.0, 35.0, 22.0, 35.0, 22.0);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_4;
g.setTransform(defaultTransform__0_0_4);
g.setClip(clip__0_0_4);
float alpha__0_0_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_5 = g.getClip();
AffineTransform defaultTransform__0_0_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_5 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(25.090908, 22.363636);
((GeneralPath)shape).curveTo(33.090908, 26.363636, 33.090908, 26.363636, 33.090908, 26.363636);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_5;
g.setTransform(defaultTransform__0_0_5);
g.setClip(clip__0_0_5);
float alpha__0_0_6 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_6 = g.getClip();
AffineTransform defaultTransform__0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_6 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.5, 20.5);
((GeneralPath)shape).curveTo(34.5, 24.5, 34.5, 24.5, 34.5, 24.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_6;
g.setTransform(defaultTransform__0_0_6);
g.setClip(clip__0_0_6);
float alpha__0_0_7 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7 = g.getClip();
AffineTransform defaultTransform__0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(20.5, 27.5);
((GeneralPath)shape).curveTo(28.5, 31.5, 28.5, 31.5, 28.5, 31.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_7;
g.setTransform(defaultTransform__0_0_7);
g.setClip(clip__0_0_7);
float alpha__0_0_8 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_8 = g.getClip();
AffineTransform defaultTransform__0_0_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_8 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(22.5, 25.5);
((GeneralPath)shape).curveTo(30.5, 29.5, 30.5, 29.5, 30.5, 29.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_8;
g.setTransform(defaultTransform__0_0_8);
g.setClip(clip__0_0_8);
float alpha__0_0_9 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_9 = g.getClip();
AffineTransform defaultTransform__0_0_9 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_9 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.5, 28.90909);
((GeneralPath)shape).curveTo(25.5, 32.90909, 25.5, 32.90909, 25.5, 32.90909);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_9;
g.setTransform(defaultTransform__0_0_9);
g.setClip(clip__0_0_9);
float alpha__0_0_10 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_10 = g.getClip();
AffineTransform defaultTransform__0_0_10 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_10 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(13.5, 31.5);
((GeneralPath)shape).curveTo(21.5, 35.5, 21.5, 35.5, 21.5, 35.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_10;
g.setTransform(defaultTransform__0_0_10);
g.setClip(clip__0_0_10);
float alpha__0_0_11 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_11 = g.getClip();
AffineTransform defaultTransform__0_0_11 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_11 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.5, 34.5);
((GeneralPath)shape).curveTo(19.5, 38.5, 19.5, 38.5, 19.5, 38.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_11;
g.setTransform(defaultTransform__0_0_11);
g.setClip(clip__0_0_11);
float alpha__0_0_12 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_12 = g.getClip();
AffineTransform defaultTransform__0_0_12 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_12 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(9.5, 37.5);
((GeneralPath)shape).curveTo(17.5, 41.5, 17.5, 41.5, 17.5, 41.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_12;
g.setTransform(defaultTransform__0_0_12);
g.setClip(clip__0_0_12);
float alpha__0_0_13 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_13 = g.getClip();
AffineTransform defaultTransform__0_0_13 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_13 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(8.5, 41.090908);
((GeneralPath)shape).curveTo(16.5, 45.090908, 16.5, 45.090908, 16.5, 45.090908);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_13;
g.setTransform(defaultTransform__0_0_13);
g.setClip(clip__0_0_13);
float alpha__0_0_14 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_14 = g.getClip();
AffineTransform defaultTransform__0_0_14 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_14 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(41.0, 23.5);
((GeneralPath)shape).curveTo(49.0, 27.5, 49.0, 27.5, 49.0, 27.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_14;
g.setTransform(defaultTransform__0_0_14);
g.setClip(clip__0_0_14);
float alpha__0_0_15 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_15 = g.getClip();
AffineTransform defaultTransform__0_0_15 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_15 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(40.5, 26.5);
((GeneralPath)shape).curveTo(48.5, 30.5, 48.5, 30.5, 48.5, 30.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_15;
g.setTransform(defaultTransform__0_0_15);
g.setClip(clip__0_0_15);
float alpha__0_0_16 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_16 = g.getClip();
AffineTransform defaultTransform__0_0_16 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_16 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(36.5, 29.5);
((GeneralPath)shape).curveTo(44.5, 33.5, 44.5, 33.5, 44.5, 33.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_16;
g.setTransform(defaultTransform__0_0_16);
g.setClip(clip__0_0_16);
float alpha__0_0_17 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_17 = g.getClip();
AffineTransform defaultTransform__0_0_17 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_17 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(34.5, 31.5);
((GeneralPath)shape).curveTo(42.5, 35.5, 42.5, 35.5, 42.5, 35.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_17;
g.setTransform(defaultTransform__0_0_17);
g.setClip(clip__0_0_17);
float alpha__0_0_18 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_18 = g.getClip();
AffineTransform defaultTransform__0_0_18 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_18 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.5, 35.5);
((GeneralPath)shape).curveTo(39.5, 39.5, 39.5, 39.5, 39.5, 39.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_18;
g.setTransform(defaultTransform__0_0_18);
g.setClip(clip__0_0_18);
float alpha__0_0_19 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_19 = g.getClip();
AffineTransform defaultTransform__0_0_19 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_19 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(29.5, 37.5);
((GeneralPath)shape).curveTo(37.5, 41.5, 37.5, 41.5, 37.5, 41.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_19;
g.setTransform(defaultTransform__0_0_19);
g.setClip(clip__0_0_19);
float alpha__0_0_20 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_20 = g.getClip();
AffineTransform defaultTransform__0_0_20 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_20 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.977272, 39.5);
((GeneralPath)shape).curveTo(34.977272, 43.5, 34.977272, 43.5, 34.977272, 43.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_20;
g.setTransform(defaultTransform__0_0_20);
g.setClip(clip__0_0_20);
float alpha__0_0_21 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_21 = g.getClip();
AffineTransform defaultTransform__0_0_21 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_21 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(25.5, 42.5);
((GeneralPath)shape).curveTo(33.5, 46.5, 33.5, 46.5, 33.5, 46.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_21;
g.setTransform(defaultTransform__0_0_21);
g.setClip(clip__0_0_21);
float alpha__0_0_22 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_22 = g.getClip();
AffineTransform defaultTransform__0_0_22 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_22 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(24.795456, 45.840908);
((GeneralPath)shape).curveTo(32.795456, 49.840908, 32.795456, 49.840908, 32.795456, 49.840908);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_22;
g.setTransform(defaultTransform__0_0_22);
g.setClip(clip__0_0_22);
float alpha__0_0_23 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_23 = g.getClip();
AffineTransform defaultTransform__0_0_23 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_23 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(24.295456, 48.340908);
((GeneralPath)shape).curveTo(32.295456, 52.340908, 32.295456, 52.340908, 32.295456, 52.340908);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_23;
g.setTransform(defaultTransform__0_0_23);
g.setClip(clip__0_0_23);
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
        return 1;
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
	public GazetteerIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public GazetteerIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public GazetteerIcon(int width, int height) {
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

