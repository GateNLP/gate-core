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
public class RefreshIcon implements
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
g.transform(new AffineTransform(-1.0f, 0.0f, 0.0f, 1.0f, 48.99482727050781f, 0.0f));
// _0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(13.478553771972656, 10.61220645904541), new Point2D.Double(15.419417381286621, 19.115121841430664), new float[] {0.0f,0.33333334f,1.0f}, new Color[] {new Color(52, 101, 164, 255),new Color(91, 134, 190, 255),new Color(131, 168, 216, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.46541300415992737f, -0.2775929868221283f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(20.152912, 10.409904);
((GeneralPath)shape).curveTo(20.152912, 10.409904, 11.215412, 9.784904, 13.965412, 20.284904);
((GeneralPath)shape).lineTo(6.277912, 20.284904);
((GeneralPath)shape).curveTo(6.277912, 20.284904, 6.777912, 8.4099045, 20.152912, 10.4099045);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(37.1280517578125, 29.729604721069336), new Point2D.Double(37.06541442871094, 26.19407081604004), new float[] {0.0f,1.0f}, new Color[] {new Color(52, 101, 164, 255),new Color(52, 101, 164, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-1.0f, 0.0f, 0.0f, -1.0f, 47.52790832519531f, 45.847408294677734f));
stroke = new BasicStroke(0.9999996f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(20.152912, 10.409904);
((GeneralPath)shape).curveTo(20.152912, 10.409904, 11.215412, 9.784904, 13.965412, 20.284904);
((GeneralPath)shape).lineTo(6.277912, 20.284904);
((GeneralPath)shape).curveTo(6.277912, 20.284904, 6.777912, 8.4099045, 20.152912, 10.4099045);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_0;
g.setTransform(defaultTransform__0_0_0_0);
g.setClip(clip__0_0_0_0);
float alpha__0_0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_1 = g.getTransform();
g.transform(new AffineTransform(-0.5790510177612305f, -0.4892280101776123f, -0.4892280101776123f, 0.5790510177612305f, 56.91585159301758f, 13.371370315551758f));
// _0_0_0_1 is CompositeGraphicsNode
float alpha__0_0_0_1_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(62.51383590698242, 36.06123733520508), new Point2D.Double(15.98486328125, 20.608579635620117), new float[] {0.0f,1.0f}, new Color[] {new Color(185, 207, 231, 255),new Color(114, 159, 207, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(44.30678, 50.229694);
((GeneralPath)shape).curveTo(62.8215, 35.81886, 49.664585, 13.411704, 22.462412, 12.49765);
((GeneralPath)shape).lineTo(22.113844, 3.151548);
((GeneralPath)shape).lineTo(7.624544, 20.496754);
((GeneralPath)shape).lineTo(22.714329, 33.21919);
((GeneralPath)shape).curveTo(22.714329, 33.21919, 22.462412, 23.337969, 22.462412, 23.337969);
((GeneralPath)shape).curveTo(41.29217, 24.336946, 55.44404, 37.4097, 44.306786, 50.229694);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(46.834815979003906, 45.264122009277344), new Point2D.Double(45.380435943603516, 50.939666748046875), new float[] {0.0f,1.0f}, new Color[] {new Color(52, 101, 164, 255),new Color(52, 101, 164, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.3191693f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(44.30678, 50.229694);
((GeneralPath)shape).curveTo(62.8215, 35.81886, 49.664585, 13.411704, 22.462412, 12.49765);
((GeneralPath)shape).lineTo(22.113844, 3.151548);
((GeneralPath)shape).lineTo(7.624544, 20.496754);
((GeneralPath)shape).lineTo(22.714329, 33.21919);
((GeneralPath)shape).curveTo(22.714329, 33.21919, 22.462412, 23.337969, 22.462412, 23.337969);
((GeneralPath)shape).curveTo(41.29217, 24.336946, 55.44404, 37.4097, 44.306786, 50.229694);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_1_0;
g.setTransform(defaultTransform__0_0_0_1_0);
g.setClip(clip__0_0_0_1_0);
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
paint = new LinearGradientPaint(new Point2D.Double(32.647972106933594, 30.74884605407715), new Point2D.Double(37.12446212768555, 24.842252731323242), new float[] {0.0f,1.0f}, new Color[] {new Color(196, 215, 235, 255),new Color(196, 215, 235, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(28.375, 33.4375);
((GeneralPath)shape).curveTo(28.375, 33.4375, 37.3125, 34.0625, 34.5625, 23.5625);
((GeneralPath)shape).lineTo(42.338387, 23.5625);
((GeneralPath)shape).curveTo(42.338387, 25.065102, 41.75, 35.4375, 28.374998, 33.4375);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(36.713836669921875, 31.455951690673828), new Point2D.Double(37.12446212768555, 24.842252731323242), new float[] {0.0f,1.0f}, new Color[] {new Color(57, 119, 195, 255),new Color(137, 174, 220, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.9999996f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(28.375, 33.4375);
((GeneralPath)shape).curveTo(28.375, 33.4375, 37.3125, 34.0625, 34.5625, 23.5625);
((GeneralPath)shape).lineTo(42.338387, 23.5625);
((GeneralPath)shape).curveTo(42.338387, 25.065102, 41.75, 35.4375, 28.374998, 33.4375);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_2;
g.setTransform(defaultTransform__0_0_0_2);
g.setClip(clip__0_0_0_2);
float alpha__0_0_0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_3 = g.getClip();
AffineTransform defaultTransform__0_0_0_3 = g.getTransform();
g.transform(new AffineTransform(0.5790510177612305f, 0.4892280101776123f, 0.4892280101776123f, -0.5790510177612305f, -7.921022891998291f, 30.53598976135254f));
// _0_0_0_3 is CompositeGraphicsNode
float alpha__0_0_0_3_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_3_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_3_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_3_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(18.935766220092773, 23.667896270751953), new Point2D.Double(53.588623046875, 26.649362564086914), new float[] {0.0f,1.0f}, new Color[] {new Color(114, 159, 207, 255),new Color(82, 138, 197, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(44.30678, 50.229694);
((GeneralPath)shape).curveTo(62.8215, 35.81886, 49.664585, 13.411704, 22.462412, 12.49765);
((GeneralPath)shape).lineTo(22.399431, 3.0690298);
((GeneralPath)shape).lineTo(7.793943, 20.424006);
((GeneralPath)shape).lineTo(22.462412, 33.006348);
((GeneralPath)shape).curveTo(22.462412, 33.006348, 22.462412, 23.337967, 22.462412, 23.337967);
((GeneralPath)shape).curveTo(41.29217, 24.336945, 55.44404, 37.409695, 44.306786, 50.22969);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(46.834815979003906, 45.264122009277344), new Point2D.Double(45.380435943603516, 50.939666748046875), new float[] {0.0f,1.0f}, new Color[] {new Color(52, 101, 164, 255),new Color(52, 101, 164, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.3191693f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(44.30678, 50.229694);
((GeneralPath)shape).curveTo(62.8215, 35.81886, 49.664585, 13.411704, 22.462412, 12.49765);
((GeneralPath)shape).lineTo(22.399431, 3.0690298);
((GeneralPath)shape).lineTo(7.793943, 20.424006);
((GeneralPath)shape).lineTo(22.462412, 33.006348);
((GeneralPath)shape).curveTo(22.462412, 33.006348, 22.462412, 23.337967, 22.462412, 23.337967);
((GeneralPath)shape).curveTo(41.29217, 24.336945, 55.44404, 37.409695, 44.306786, 50.22969);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_3_0;
g.setTransform(defaultTransform__0_0_0_3_0);
g.setClip(clip__0_0_0_3_0);
origAlpha = alpha__0_0_0_3;
g.setTransform(defaultTransform__0_0_0_3);
g.setClip(clip__0_0_0_3);
float alpha__0_0_0_4 = origAlpha;
origAlpha = origAlpha * 0.27222225f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_4 = g.getClip();
AffineTransform defaultTransform__0_0_0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_4 is ShapeNode
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(7.0625, 38.1875);
((GeneralPath)shape).lineTo(7.125, 23.3125);
((GeneralPath)shape).lineTo(20.0625, 22.9375);
((GeneralPath)shape).lineTo(15.673627, 28.116318);
((GeneralPath)shape).lineTo(19.540852, 30.489517);
((GeneralPath)shape).curveTo(16.540852, 32.739517, 14.991303, 32.911644, 13.991303, 35.474144);
((GeneralPath)shape).lineTo(11.174446, 33.363873);
((GeneralPath)shape).lineTo(7.0625, 38.1875);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_4;
g.setTransform(defaultTransform__0_0_0_4);
g.setClip(clip__0_0_0_4);
float alpha__0_0_0_5 = origAlpha;
origAlpha = origAlpha * 0.5f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_5 = g.getClip();
AffineTransform defaultTransform__0_0_0_5 = g.getTransform();
g.transform(new AffineTransform(0.5085359811782837f, 0.4296509921550751f, 0.4296509921550751f, -0.5085359811782837f, -3.9731879234313965f, 30.541189193725586f));
// _0_0_0_5 is CompositeGraphicsNode
float alpha__0_0_0_5_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_5_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_5_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_5_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(5.9649176597595215, 26.04816436767578), new Point2D.Double(52.854095458984375, 26.04816436767578), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.5020893f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(51.090263, 45.943707);
((GeneralPath)shape).curveTo(60.210464, 30.723955, 46.631615, 12.20113, 19.485058, 11.948579);
((GeneralPath)shape).lineTo(19.513464, 3.7032833);
((GeneralPath)shape).lineTo(6.534198, 19.296638);
((GeneralPath)shape).lineTo(19.36766, 30.26876);
((GeneralPath)shape).curveTo(19.36766, 30.26876, 19.42328, 21.261883, 19.42328, 21.261883);
((GeneralPath)shape).curveTo(36.951096, 21.037973, 54.61847, 31.365253, 51.090263, 45.943707);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_5_0;
g.setTransform(defaultTransform__0_0_0_5_0);
g.setClip(clip__0_0_0_5_0);
origAlpha = alpha__0_0_0_5;
g.setTransform(defaultTransform__0_0_0_5);
g.setClip(clip__0_0_0_5);
float alpha__0_0_0_6 = origAlpha;
origAlpha = origAlpha * 0.5f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_6 = g.getClip();
AffineTransform defaultTransform__0_0_0_6 = g.getTransform();
g.transform(new AffineTransform(-0.5085359811782837f, -0.4296509921550751f, -0.4296509921550751f, 0.5085359811782837f, 53.04899978637695f, 13.365480422973633f));
// _0_0_0_6 is CompositeGraphicsNode
float alpha__0_0_0_6_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_6_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_6_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_6_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(5.9649176597595215, 26.04816436767578), new Point2D.Double(52.854095458984375, 26.04816436767578), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.5020893f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(51.389927, 46.505947);
((GeneralPath)shape).curveTo(60.510128, 31.286196, 47.050762, 12.432359, 19.628483, 12.069755);
((GeneralPath)shape).lineTo(19.342825, 4.05072);
((GeneralPath)shape).lineTo(6.341309, 19.379475);
((GeneralPath)shape).lineTo(19.80906, 30.76459);
((GeneralPath)shape).curveTo(19.80906, 30.76459, 19.627295, 21.311346, 19.627295, 21.311346);
((GeneralPath)shape).curveTo(37.87223, 21.693317, 54.41118, 32.23659, 51.389927, 46.505943);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_6_0;
g.setTransform(defaultTransform__0_0_0_6_0);
g.setClip(clip__0_0_0_6_0);
origAlpha = alpha__0_0_0_6;
g.setTransform(defaultTransform__0_0_0_6);
g.setClip(clip__0_0_0_6);
float alpha__0_0_0_7 = origAlpha;
origAlpha = origAlpha * 0.27222225f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_7 = g.getClip();
AffineTransform defaultTransform__0_0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_7 is ShapeNode
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(6.8125, 16.5);
((GeneralPath)shape).curveTo(10.405935, 6.0587273, 23.256283, 10.355393, 27.0, 12.0);
((GeneralPath)shape).curveTo(31.175306, 12.211475, 32.674736, 9.164996, 36.0, 9.0);
((GeneralPath)shape).curveTo(21.950264, -0.7899963, 7.1875, 2.5, 6.8125, 16.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_7;
g.setTransform(defaultTransform__0_0_0_7);
g.setClip(clip__0_0_0_7);
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
	public RefreshIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public RefreshIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public RefreshIcon(int width, int height) {
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

