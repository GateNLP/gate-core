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
public class lrsIcon implements
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.590910017490387f, -0.22727300226688385f));
// _0_0_0 is ShapeNode
paint = new Color(0, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(34.90909, 31.818182);
((GeneralPath)shape).curveTo(34.909805, 32.857994, 34.37282, 33.819138, 33.500576, 34.339256);
((GeneralPath)shape).curveTo(32.628334, 34.859375, 31.55348, 34.859375, 30.681236, 34.339256);
((GeneralPath)shape).curveTo(29.808994, 33.819138, 29.272009, 32.857994, 29.272726, 31.818182);
((GeneralPath)shape).curveTo(29.272009, 30.77837, 29.808994, 29.817228, 30.681236, 29.297108);
((GeneralPath)shape).curveTo(31.55348, 28.776989, 32.628334, 28.776989, 33.500576, 29.297108);
((GeneralPath)shape).curveTo(34.37282, 29.817228, 34.909805, 30.77837, 34.90909, 31.818182);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(34.90909, 31.818182);
((GeneralPath)shape).curveTo(34.909805, 32.857994, 34.37282, 33.819138, 33.500576, 34.339256);
((GeneralPath)shape).curveTo(32.628334, 34.859375, 31.55348, 34.859375, 30.681236, 34.339256);
((GeneralPath)shape).curveTo(29.808994, 33.819138, 29.272009, 32.857994, 29.272726, 31.818182);
((GeneralPath)shape).curveTo(29.272009, 30.77837, 29.808994, 29.817228, 30.681236, 29.297108);
((GeneralPath)shape).curveTo(31.55348, 28.776989, 32.628334, 28.776989, 33.500576, 29.297108);
((GeneralPath)shape).curveTo(34.37282, 29.817228, 34.909805, 30.77837, 34.90909, 31.818182);
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
g.transform(new AffineTransform(0.6733329892158508f, 0.0f, 0.0f, 0.8561069965362549f, 25.565080642700195f, 2.9319379329681396f));
// _0_0_1 is CompositeGraphicsNode
float alpha__0_0_1_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_0 = g.getClip();
AffineTransform defaultTransform__0_0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_0 is ShapeNode
paint = new Color(255, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(34.36364, 27.454546);
((GeneralPath)shape).curveTo(33.454548, 42.967274, 50.817455, 49.090908, 31.363638, 42.181816);
((GeneralPath)shape).curveTo(20.91298, 38.47023, 13.09091, 37.14909, 13.09091, 33.636364);
((GeneralPath)shape).curveTo(13.09091, 30.123636, 20.86385, 26.779789, 30.27273, 20.909092);
((GeneralPath)shape).curveTo(51.544727, 7.6363635, 33.818184, 17.214546, 34.36364, 27.454546);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(34.36364, 27.454546);
((GeneralPath)shape).curveTo(33.454548, 42.967274, 50.817455, 49.090908, 31.363638, 42.181816);
((GeneralPath)shape).curveTo(20.91298, 38.47023, 13.09091, 37.14909, 13.09091, 33.636364);
((GeneralPath)shape).curveTo(13.09091, 30.123636, 20.86385, 26.779789, 30.27273, 20.909092);
((GeneralPath)shape).curveTo(51.544727, 7.6363635, 33.818184, 17.214546, 34.36364, 27.454546);
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
g.transform(new AffineTransform(0.523635983467102f, 0.0f, -0.028865160420536995f, 1.3374489545822144f, 11.051779747009277f, -6.640699863433838f));
// _0_0_1_1 is ShapeNode
paint = new Color(7, 4, 4, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
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
g.transform(new AffineTransform(0.523635983467102f, 0.0f, -0.028865160420536995f, 1.3374489545822144f, 11.327190399169922f, -6.5217180252075195f));
// _0_0_1_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(75.63566589355469, 27.636363983154297), new Point2D.Double(36.353511810302734, 27.636363983154297), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 0, 0, 255),new Color(255, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_1_2;
g.setTransform(defaultTransform__0_0_1_2);
g.setClip(clip__0_0_1_2);
float alpha__0_0_1_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_3 = g.getClip();
AffineTransform defaultTransform__0_0_1_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_3 is ShapeNode
paint = new Color(253, 253, 253, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.636362, 29.999998);
((GeneralPath)shape).curveTo(19.350975, 26.951801, 23.675842, 25.347046, 22.0, 27.454544);
((GeneralPath)shape).curveTo(19.0, 31.227272, 22.0, 38.727272, 22.0, 38.727272);
((GeneralPath)shape).lineTo(18.545454, 37.272724);
((GeneralPath)shape).curveTo(18.545454, 37.272724, 15.999999, 32.90909, 17.636362, 29.999998);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_1_3;
g.setTransform(defaultTransform__0_0_1_3);
g.setClip(clip__0_0_1_3);
float alpha__0_0_1_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_4 = g.getClip();
AffineTransform defaultTransform__0_0_1_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_4 is ShapeNode
paint = new Color(253, 253, 253, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(23.621086, 26.957462);
((GeneralPath)shape).curveTo(25.335697, 23.909266, 30.387838, 21.031782, 28.711996, 23.139282);
((GeneralPath)shape).curveTo(25.711996, 26.912008, 22.823318, 29.289742, 27.802904, 36.593826);
((GeneralPath)shape).curveTo(32.697533, 43.773296, 24.530176, 39.139282, 24.530176, 39.139282);
((GeneralPath)shape).curveTo(24.530176, 39.139282, 21.984722, 29.866554, 23.621086, 26.957462);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_1_4;
g.setTransform(defaultTransform__0_0_1_4);
g.setClip(clip__0_0_1_4);
origAlpha = alpha__0_0_1;
g.setTransform(defaultTransform__0_0_1);
g.setClip(clip__0_0_1);
float alpha__0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(0.0f, -0.6733329892158508f, 0.8561069965362549f, 0.0f, 3.931938886642456f, 39.43492126464844f));
// _0_0_2 is CompositeGraphicsNode
float alpha__0_0_2_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_0 = g.getClip();
AffineTransform defaultTransform__0_0_2_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_0 is ShapeNode
paint = new Color(255, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(34.36364, 27.454546);
((GeneralPath)shape).curveTo(33.454548, 42.967274, 50.817455, 49.090908, 31.363638, 42.181816);
((GeneralPath)shape).curveTo(20.91298, 38.47023, 13.09091, 37.14909, 13.09091, 33.636364);
((GeneralPath)shape).curveTo(13.09091, 30.123636, 20.86385, 26.779789, 30.27273, 20.909092);
((GeneralPath)shape).curveTo(51.544727, 7.6363635, 33.818184, 17.214546, 34.36364, 27.454546);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(34.36364, 27.454546);
((GeneralPath)shape).curveTo(33.454548, 42.967274, 50.817455, 49.090908, 31.363638, 42.181816);
((GeneralPath)shape).curveTo(20.91298, 38.47023, 13.09091, 37.14909, 13.09091, 33.636364);
((GeneralPath)shape).curveTo(13.09091, 30.123636, 20.86385, 26.779789, 30.27273, 20.909092);
((GeneralPath)shape).curveTo(51.544727, 7.6363635, 33.818184, 17.214546, 34.36364, 27.454546);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2_0;
g.setTransform(defaultTransform__0_0_2_0);
g.setClip(clip__0_0_2_0);
float alpha__0_0_2_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_1 = g.getClip();
AffineTransform defaultTransform__0_0_2_1 = g.getTransform();
g.transform(new AffineTransform(0.523635983467102f, 0.0f, -0.028865160420536995f, 1.3374489545822144f, 11.051779747009277f, -6.640699863433838f));
// _0_0_2_1 is ShapeNode
paint = new Color(7, 4, 4, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
((GeneralPath)shape).closePath();
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
g.transform(new AffineTransform(0.523635983467102f, 0.0f, -0.028865160420536995f, 1.3374489545822144f, 11.327190399169922f, -6.5217180252075195f));
// _0_0_2_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(75.63566589355469, 27.636363983154297), new Point2D.Double(36.353511810302734, 27.636363983154297), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 0, 0, 255),new Color(255, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
((GeneralPath)shape).closePath();
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_3 is ShapeNode
paint = new Color(253, 253, 253, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.636362, 29.999998);
((GeneralPath)shape).curveTo(19.350975, 26.951801, 23.675842, 25.347046, 22.0, 27.454544);
((GeneralPath)shape).curveTo(19.0, 31.227272, 22.0, 38.727272, 22.0, 38.727272);
((GeneralPath)shape).lineTo(18.545454, 37.272724);
((GeneralPath)shape).curveTo(18.545454, 37.272724, 15.999999, 32.90909, 17.636362, 29.999998);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = new Color(253, 253, 253, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(23.621086, 26.957462);
((GeneralPath)shape).curveTo(25.335697, 23.909266, 30.387838, 21.031782, 28.711996, 23.139282);
((GeneralPath)shape).curveTo(25.711996, 26.912008, 22.823318, 29.289742, 27.802904, 36.593826);
((GeneralPath)shape).curveTo(32.697533, 43.773296, 24.530176, 39.139282, 24.530176, 39.139282);
((GeneralPath)shape).curveTo(24.530176, 39.139282, 21.984722, 29.866554, 23.621086, 26.957462);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_4;
g.setTransform(defaultTransform__0_0_2_4);
g.setClip(clip__0_0_2_4);
origAlpha = alpha__0_0_2;
g.setTransform(defaultTransform__0_0_2);
g.setClip(clip__0_0_2);
float alpha__0_0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3 = g.getClip();
AffineTransform defaultTransform__0_0_3 = g.getTransform();
g.transform(new AffineTransform(-0.6733329892158508f, 0.0f, 0.0f, -0.8561069965362549f, 40.23469924926758f, 60.950740814208984f));
// _0_0_3 is CompositeGraphicsNode
float alpha__0_0_3_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3_0 = g.getClip();
AffineTransform defaultTransform__0_0_3_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3_0 is ShapeNode
paint = new Color(255, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(34.36364, 27.454546);
((GeneralPath)shape).curveTo(33.454548, 42.967274, 50.817455, 49.090908, 31.363638, 42.181816);
((GeneralPath)shape).curveTo(20.91298, 38.47023, 13.09091, 37.14909, 13.09091, 33.636364);
((GeneralPath)shape).curveTo(13.09091, 30.123636, 20.86385, 26.779789, 30.27273, 20.909092);
((GeneralPath)shape).curveTo(51.544727, 7.6363635, 33.818184, 17.214546, 34.36364, 27.454546);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(34.36364, 27.454546);
((GeneralPath)shape).curveTo(33.454548, 42.967274, 50.817455, 49.090908, 31.363638, 42.181816);
((GeneralPath)shape).curveTo(20.91298, 38.47023, 13.09091, 37.14909, 13.09091, 33.636364);
((GeneralPath)shape).curveTo(13.09091, 30.123636, 20.86385, 26.779789, 30.27273, 20.909092);
((GeneralPath)shape).curveTo(51.544727, 7.6363635, 33.818184, 17.214546, 34.36364, 27.454546);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_3_0;
g.setTransform(defaultTransform__0_0_3_0);
g.setClip(clip__0_0_3_0);
float alpha__0_0_3_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3_1 = g.getClip();
AffineTransform defaultTransform__0_0_3_1 = g.getTransform();
g.transform(new AffineTransform(0.523635983467102f, 0.0f, -0.028865160420536995f, 1.3374489545822144f, 11.051779747009277f, -6.640699863433838f));
// _0_0_3_1 is ShapeNode
paint = new Color(7, 4, 4, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_3_1;
g.setTransform(defaultTransform__0_0_3_1);
g.setClip(clip__0_0_3_1);
float alpha__0_0_3_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3_2 = g.getClip();
AffineTransform defaultTransform__0_0_3_2 = g.getTransform();
g.transform(new AffineTransform(0.523635983467102f, 0.0f, -0.028865160420536995f, 1.3374489545822144f, 11.327190399169922f, -6.5217180252075195f));
// _0_0_3_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(75.63566589355469, 27.636363983154297), new Point2D.Double(36.353511810302734, 27.636363983154297), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 0, 0, 255),new Color(255, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_3_2;
g.setTransform(defaultTransform__0_0_3_2);
g.setClip(clip__0_0_3_2);
float alpha__0_0_3_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3_3 = g.getClip();
AffineTransform defaultTransform__0_0_3_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3_3 is ShapeNode
paint = new Color(253, 253, 253, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.636362, 29.999998);
((GeneralPath)shape).curveTo(19.350975, 26.951801, 23.675842, 25.347046, 22.0, 27.454544);
((GeneralPath)shape).curveTo(19.0, 31.227272, 22.0, 38.727272, 22.0, 38.727272);
((GeneralPath)shape).lineTo(18.545454, 37.272724);
((GeneralPath)shape).curveTo(18.545454, 37.272724, 15.999999, 32.90909, 17.636362, 29.999998);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_3_3;
g.setTransform(defaultTransform__0_0_3_3);
g.setClip(clip__0_0_3_3);
float alpha__0_0_3_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3_4 = g.getClip();
AffineTransform defaultTransform__0_0_3_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3_4 is ShapeNode
paint = new Color(253, 253, 253, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(23.621086, 26.957462);
((GeneralPath)shape).curveTo(25.335697, 23.909266, 30.387838, 21.031782, 28.711996, 23.139282);
((GeneralPath)shape).curveTo(25.711996, 26.912008, 22.823318, 29.289742, 27.802904, 36.593826);
((GeneralPath)shape).curveTo(32.697533, 43.773296, 24.530176, 39.139282, 24.530176, 39.139282);
((GeneralPath)shape).curveTo(24.530176, 39.139282, 21.984722, 29.866554, 23.621086, 26.957462);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_3_4;
g.setTransform(defaultTransform__0_0_3_4);
g.setClip(clip__0_0_3_4);
origAlpha = alpha__0_0_3;
g.setTransform(defaultTransform__0_0_3);
g.setClip(clip__0_0_3);
float alpha__0_0_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_4 = g.getClip();
AffineTransform defaultTransform__0_0_4 = g.getTransform();
g.transform(new AffineTransform(0.0f, 0.6733329892158508f, -0.8561069965362549f, 0.0f, 62.06806182861328f, 23.765300750732422f));
// _0_0_4 is CompositeGraphicsNode
float alpha__0_0_4_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_4_0 = g.getClip();
AffineTransform defaultTransform__0_0_4_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_4_0 is ShapeNode
paint = new Color(255, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(34.36364, 27.454546);
((GeneralPath)shape).curveTo(33.454548, 42.967274, 50.817455, 49.090908, 31.363638, 42.181816);
((GeneralPath)shape).curveTo(20.91298, 38.47023, 13.09091, 37.14909, 13.09091, 33.636364);
((GeneralPath)shape).curveTo(13.09091, 30.123636, 20.86385, 26.779789, 30.27273, 20.909092);
((GeneralPath)shape).curveTo(51.544727, 7.6363635, 33.818184, 17.214546, 34.36364, 27.454546);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(34.36364, 27.454546);
((GeneralPath)shape).curveTo(33.454548, 42.967274, 50.817455, 49.090908, 31.363638, 42.181816);
((GeneralPath)shape).curveTo(20.91298, 38.47023, 13.09091, 37.14909, 13.09091, 33.636364);
((GeneralPath)shape).curveTo(13.09091, 30.123636, 20.86385, 26.779789, 30.27273, 20.909092);
((GeneralPath)shape).curveTo(51.544727, 7.6363635, 33.818184, 17.214546, 34.36364, 27.454546);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_4_0;
g.setTransform(defaultTransform__0_0_4_0);
g.setClip(clip__0_0_4_0);
float alpha__0_0_4_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_4_1 = g.getClip();
AffineTransform defaultTransform__0_0_4_1 = g.getTransform();
g.transform(new AffineTransform(0.523635983467102f, 0.0f, -0.028865160420536995f, 1.3374489545822144f, 11.051779747009277f, -6.640699863433838f));
// _0_0_4_1 is ShapeNode
paint = new Color(7, 4, 4, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_4_1;
g.setTransform(defaultTransform__0_0_4_1);
g.setClip(clip__0_0_4_1);
float alpha__0_0_4_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_4_2 = g.getClip();
AffineTransform defaultTransform__0_0_4_2 = g.getTransform();
g.transform(new AffineTransform(0.523635983467102f, 0.0f, -0.028865160420536995f, 1.3374489545822144f, 11.327190399169922f, -6.5217180252075195f));
// _0_0_4_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(75.63566589355469, 27.636363983154297), new Point2D.Double(36.353511810302734, 27.636363983154297), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 0, 0, 255),new Color(255, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_4_2;
g.setTransform(defaultTransform__0_0_4_2);
g.setClip(clip__0_0_4_2);
float alpha__0_0_4_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_4_3 = g.getClip();
AffineTransform defaultTransform__0_0_4_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_4_3 is ShapeNode
paint = new Color(253, 253, 253, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.636362, 29.999998);
((GeneralPath)shape).curveTo(19.350975, 26.951801, 23.675842, 25.347046, 22.0, 27.454544);
((GeneralPath)shape).curveTo(19.0, 31.227272, 22.0, 38.727272, 22.0, 38.727272);
((GeneralPath)shape).lineTo(18.545454, 37.272724);
((GeneralPath)shape).curveTo(18.545454, 37.272724, 15.999999, 32.90909, 17.636362, 29.999998);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_4_3;
g.setTransform(defaultTransform__0_0_4_3);
g.setClip(clip__0_0_4_3);
float alpha__0_0_4_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_4_4 = g.getClip();
AffineTransform defaultTransform__0_0_4_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_4_4 is ShapeNode
paint = new Color(253, 253, 253, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(23.621086, 26.957462);
((GeneralPath)shape).curveTo(25.335697, 23.909266, 30.387838, 21.031782, 28.711996, 23.139282);
((GeneralPath)shape).curveTo(25.711996, 26.912008, 22.823318, 29.289742, 27.802904, 36.593826);
((GeneralPath)shape).curveTo(32.697533, 43.773296, 24.530176, 39.139282, 24.530176, 39.139282);
((GeneralPath)shape).curveTo(24.530176, 39.139282, 21.984722, 29.866554, 23.621086, 26.957462);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_4_4;
g.setTransform(defaultTransform__0_0_4_4);
g.setClip(clip__0_0_4_4);
origAlpha = alpha__0_0_4;
g.setTransform(defaultTransform__0_0_4);
g.setClip(clip__0_0_4);
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
        return 5;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 5;
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
	public lrsIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public lrsIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public lrsIcon(int width, int height) {
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

