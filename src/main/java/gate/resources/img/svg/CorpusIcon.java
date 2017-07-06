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
public class CorpusIcon implements
		javax.swing.Icon {
		
	private static Color getColor(int red, int green, int blue, int alpha, boolean disabled) {
		
		if (!disabled) return new Color(red, green, blue, alpha);
		
		int gray = (int)(((0.30f * red) + (0.59f * green) + (0.11f * blue))/3f);
		
		gray = Math.min(255, Math.max(0, gray));
		
		//This brightens the image the same as GrayFilter
		int percent = 50;		
		gray = (255 - ((255 - gray) * (100 - percent) / 100));

		return new Color(gray, gray, gray, alpha);
	}
	
	/**
	 * Paints the transcoded SVG image on the specified graphics context. You
	 * can install a custom transformation on the graphics context to scale the
	 * image.
	 * 
	 * @param g
	 *            Graphics context.
	 */
	public static void paint(Graphics2D g, boolean disabled) {
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
g.transform(new AffineTransform(0.9105449914932251f, 0.4134100079536438f, -0.6455870270729065f, 0.763687014579773f, 0.0f, 0.0f));
// _0_0_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(42.18181610107422, 29.27272605895996), new Point2D.Double(10.363636016845703, 60.54545211791992), new float[] {0.0f,1.0f}, new Color[] {getColor(255, 255, 255, 255, disabled),getColor(6, 1, 1, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9779120087623596f, -2.3235840274082875E-7f, -3.2074518685476505E-7f, 1.084488034248352f, 19.942699432373047f, -14.149840354919434f));
shape = new Rectangle2D.Double(34.910888671875, -2.0730528831481934, 33.21522903442383, 47.521339416503906);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.4769982f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(34.910888671875, -2.0730528831481934, 33.21522903442383, 47.521339416503906);
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
g.transform(new AffineTransform(0.9105449914932251f, 0.4134100079536438f, -0.6455870270729065f, 0.763687014579773f, 0.0f, 0.0f));
// _0_0_1 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(42.18181610107422, 29.27272605895996), new Point2D.Double(10.363636016845703, 60.54545211791992), new float[] {0.0f,1.0f}, new Color[] {getColor(255, 255, 255, 255, disabled),getColor(6, 1, 1, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9779120087623596f, -2.3235840274082875E-7f, -3.2074518685476505E-7f, 1.084488034248352f, 17.575599670410156f, -17.48843002319336f));
shape = new Rectangle2D.Double(32.54378890991211, -5.411643028259277, 33.21522903442383, 47.521385192871094);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.4769987f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(32.54378890991211, -5.411643028259277, 33.21522903442383, 47.521385192871094);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_1;
g.setTransform(defaultTransform__0_0_1);
g.setClip(clip__0_0_1);
float alpha__0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(0.9105449914932251f, 0.4134100079536438f, -0.6455870270729065f, 0.763687014579773f, 0.0f, 0.0f));
// _0_0_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(42.18181610107422, 29.27272605895996), new Point2D.Double(10.363636016845703, 60.54545211791992), new float[] {0.0f,1.0f}, new Color[] {getColor(255, 255, 255, 255, disabled),getColor(6, 1, 1, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9779120087623596f, -2.3235840274082875E-7f, -3.2074518685476505E-7f, 1.084488034248352f, 15.246359825134277f, -20.77362060546875f));
shape = new Rectangle2D.Double(30.21455192565918, -8.696839332580566, 33.21522903442383, 47.5213737487793);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.4769986f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(30.21455192565918, -8.696839332580566, 33.21522903442383, 47.5213737487793);
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
g.transform(new AffineTransform(0.9105449914932251f, 0.4134100079536438f, -0.6455870270729065f, 0.763687014579773f, 0.0f, 0.0f));
// _0_0_3 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(42.18181610107422, 29.27272605895996), new Point2D.Double(10.363636016845703, 60.54545211791992), new float[] {0.0f,1.0f}, new Color[] {getColor(255, 255, 255, 255, disabled),getColor(6, 1, 1, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9779120087623596f, -2.3235840274082875E-7f, -3.2074518685476505E-7f, 1.084488034248352f, 12.44182014465332f, -24.656719207763672f));
shape = new Rectangle2D.Double(27.410011291503906, -12.579949378967285, 33.21522903442383, 47.521339416503906);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.4769982f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(27.410011291503906, -12.579949378967285, 33.21522903442383, 47.521339416503906);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_3;
g.setTransform(defaultTransform__0_0_3);
g.setClip(clip__0_0_3);
float alpha__0_0_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_4 = g.getClip();
AffineTransform defaultTransform__0_0_4 = g.getTransform();
g.transform(new AffineTransform(0.8727239966392517f, 0.4041920006275177f, -0.6862050294876099f, 0.828029990196228f, 0.0f, 0.0f));
// _0_0_4 is TextNode of 'GATE  CORPUS'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(33.216396, -6.2600894);
((GeneralPath)shape).lineTo(33.216396, -7.042931);
((GeneralPath)shape).lineTo(32.57163, -7.042931);
((GeneralPath)shape).lineTo(32.57163, -7.36727);
((GeneralPath)shape).lineTo(33.607166, -7.36727);
((GeneralPath)shape).lineTo(33.607166, -6.1155047);
((GeneralPath)shape).quadTo(33.377914, -5.952684, 33.102425, -5.86932);
((GeneralPath)shape).quadTo(32.82693, -5.7859554, 32.514313, -5.7859554);
((GeneralPath)shape).quadTo(31.830467, -5.7859554, 31.444908, -6.185843);
((GeneralPath)shape).quadTo(31.05935, -6.5857306, 31.05935, -7.299536);
((GeneralPath)shape).quadTo(31.05935, -8.014645, 31.444908, -8.413881);
((GeneralPath)shape).quadTo(31.830467, -8.813117, 32.514313, -8.813117);
((GeneralPath)shape).quadTo(32.799576, -8.813117, 33.05683, -8.742779);
((GeneralPath)shape).quadTo(33.31409, -8.672441, 33.530315, -8.535671);
((GeneralPath)shape).lineTo(33.530315, -8.116245);
((GeneralPath)shape).quadTo(33.311485, -8.301209, 33.0653, -8.394994);
((GeneralPath)shape).quadTo(32.819115, -8.488778, 32.54818, -8.488778);
((GeneralPath)shape).quadTo(32.01283, -8.488778, 31.743847, -8.189839);
((GeneralPath)shape).quadTo(31.474867, -7.890901, 31.474867, -7.299536);
((GeneralPath)shape).quadTo(31.474867, -6.7094746, 31.743847, -6.410536);
((GeneralPath)shape).quadTo(32.01283, -6.111597, 32.54818, -6.111597);
((GeneralPath)shape).quadTo(32.75659, -6.111597, 32.920715, -6.1474175);
((GeneralPath)shape).quadTo(33.08484, -6.183238, 33.216396, -6.2600894);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(35.302456, -8.371548);
((GeneralPath)shape).lineTo(34.7671, -6.92049);
((GeneralPath)shape).lineTo(35.840416, -6.92049);
((GeneralPath)shape).lineTo(35.302456, -8.371548);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(35.079716, -8.761015);
((GeneralPath)shape).lineTo(35.527798, -8.761015);
((GeneralPath)shape).lineTo(36.638885, -5.8432684);
((GeneralPath)shape).lineTo(36.22858, -5.8432684);
((GeneralPath)shape).lineTo(35.962856, -6.5922437);
((GeneralPath)shape).lineTo(34.648567, -6.5922437);
((GeneralPath)shape).lineTo(34.382843, -5.8432684);
((GeneralPath)shape).lineTo(33.966022, -5.8432684);
((GeneralPath)shape).lineTo(35.079716, -8.761015);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(36.66038, -8.761015);
((GeneralPath)shape).lineTo(39.128742, -8.761015);
((GeneralPath)shape).lineTo(39.128742, -8.428861);
((GeneralPath)shape).lineTo(38.093204, -8.428861);
((GeneralPath)shape).lineTo(38.093204, -5.8432684);
((GeneralPath)shape).lineTo(37.69592, -5.8432684);
((GeneralPath)shape).lineTo(37.69592, -8.428861);
((GeneralPath)shape).lineTo(36.66038, -8.428861);
((GeneralPath)shape).lineTo(36.66038, -8.761015);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(39.509743, -8.761015);
((GeneralPath)shape).lineTo(41.354176, -8.761015);
((GeneralPath)shape).lineTo(41.354176, -8.428861);
((GeneralPath)shape).lineTo(39.90442, -8.428861);
((GeneralPath)shape).lineTo(39.90442, -7.56526);
((GeneralPath)shape).lineTo(41.292953, -7.56526);
((GeneralPath)shape).lineTo(41.292953, -7.2331057);
((GeneralPath)shape).lineTo(39.90442, -7.2331057);
((GeneralPath)shape).lineTo(39.90442, -6.1754227);
((GeneralPath)shape).lineTo(41.389343, -6.1754227);
((GeneralPath)shape).lineTo(41.389343, -5.8432684);
((GeneralPath)shape).lineTo(39.509743, -5.8432684);
((GeneralPath)shape).lineTo(39.509743, -8.761015);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(46.766335, -8.535671);
((GeneralPath)shape).lineTo(46.766335, -8.120152);
((GeneralPath)shape).quadTo(46.56704, -8.305117, 46.341045, -8.396948);
((GeneralPath)shape).quadTo(46.11505, -8.488778, 45.86105, -8.488778);
((GeneralPath)shape).quadTo(45.360867, -8.488778, 45.095142, -8.183327);
((GeneralPath)shape).quadTo(44.82942, -7.8778753, 44.82942, -7.299536);
((GeneralPath)shape).quadTo(44.82942, -6.7225, 45.095142, -6.4170485);
((GeneralPath)shape).quadTo(45.360867, -6.111597, 45.86105, -6.111597);
((GeneralPath)shape).quadTo(46.11505, -6.111597, 46.341045, -6.203428);
((GeneralPath)shape).quadTo(46.56704, -6.2952585, 46.766335, -6.4802227);
((GeneralPath)shape).lineTo(46.766335, -6.068612);
((GeneralPath)shape).quadTo(46.559227, -5.927935, 46.32737, -5.8569455);
((GeneralPath)shape).quadTo(46.095512, -5.7859554, 45.837605, -5.7859554);
((GeneralPath)shape).quadTo(45.1759, -5.7859554, 44.7949, -6.1917048);
((GeneralPath)shape).quadTo(44.413902, -6.5974536, 44.413902, -7.299536);
((GeneralPath)shape).quadTo(44.413902, -8.002922, 44.7949, -8.408019);
((GeneralPath)shape).quadTo(45.1759, -8.813117, 45.837605, -8.813117);
((GeneralPath)shape).quadTo(46.09942, -8.813117, 46.331276, -8.7440815);
((GeneralPath)shape).quadTo(46.563133, -8.675045, 46.766335, -8.535671);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(48.559967, -8.492686);
((GeneralPath)shape).quadTo(48.13012, -8.492686, 47.87677, -8.1722555);
((GeneralPath)shape).quadTo(47.62342, -7.8518243, 47.62342, -7.299536);
((GeneralPath)shape).quadTo(47.62342, -6.7485514, 47.87677, -6.42812);
((GeneralPath)shape).quadTo(48.13012, -6.1076894, 48.559967, -6.1076894);
((GeneralPath)shape).quadTo(48.98981, -6.1076894, 49.240555, -6.42812);
((GeneralPath)shape).quadTo(49.4913, -6.7485514, 49.4913, -7.299536);
((GeneralPath)shape).quadTo(49.4913, -7.8518243, 49.240555, -8.1722555);
((GeneralPath)shape).quadTo(48.98981, -8.492686, 48.559967, -8.492686);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(48.559967, -8.813117);
((GeneralPath)shape).quadTo(49.173473, -8.813117, 49.5408, -8.402158);
((GeneralPath)shape).quadTo(49.90812, -7.9911985, 49.90812, -7.299536);
((GeneralPath)shape).quadTo(49.90812, -6.6091766, 49.5408, -6.197566);
((GeneralPath)shape).quadTo(49.173473, -5.7859554, 48.559967, -5.7859554);
((GeneralPath)shape).quadTo(47.94385, -5.7859554, 47.57588, -6.1969147);
((GeneralPath)shape).quadTo(47.207905, -6.6078744, 47.207905, -7.299536);
((GeneralPath)shape).quadTo(47.207905, -7.9911985, 47.57588, -8.402158);
((GeneralPath)shape).quadTo(47.94385, -8.813117, 48.559967, -8.813117);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(51.908863, -7.210962);
((GeneralPath)shape).quadTo(52.035213, -7.1679773, 52.1557, -7.0273004);
((GeneralPath)shape).quadTo(52.276188, -6.8866234, 52.397324, -6.6404386);
((GeneralPath)shape).lineTo(52.797215, -5.8432684);
((GeneralPath)shape).lineTo(52.37388, -5.8432684);
((GeneralPath)shape).lineTo(52.000042, -6.5922437);
((GeneralPath)shape).quadTo(51.855457, -6.8853207, 51.71999, -6.981059);
((GeneralPath)shape).quadTo(51.584526, -7.076798, 51.350063, -7.076798);
((GeneralPath)shape).lineTo(50.920216, -7.076798);
((GeneralPath)shape).lineTo(50.920216, -5.8432684);
((GeneralPath)shape).lineTo(50.52554, -5.8432684);
((GeneralPath)shape).lineTo(50.52554, -8.761015);
((GeneralPath)shape).lineTo(51.416492, -8.761015);
((GeneralPath)shape).quadTo(51.91668, -8.761015, 52.162865, -8.551953);
((GeneralPath)shape).quadTo(52.40905, -8.342892, 52.40905, -7.9208603);
((GeneralPath)shape).quadTo(52.40905, -7.6447163, 52.280746, -7.4630084);
((GeneralPath)shape).quadTo(52.152443, -7.2813005, 51.908863, -7.210962);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(50.920216, -8.436676);
((GeneralPath)shape).lineTo(50.920216, -7.4011364);
((GeneralPath)shape).lineTo(51.416492, -7.4011364);
((GeneralPath)shape).quadTo(51.701756, -7.4011364, 51.846992, -7.532696);
((GeneralPath)shape).quadTo(51.992226, -7.6642547, 51.992226, -7.9208603);
((GeneralPath)shape).quadTo(51.992226, -8.176163, 51.846992, -8.306419);
((GeneralPath)shape).quadTo(51.701756, -8.436676, 51.416492, -8.436676);
((GeneralPath)shape).lineTo(50.920216, -8.436676);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(53.70054, -8.436676);
((GeneralPath)shape).lineTo(53.70054, -7.3399158);
((GeneralPath)shape).lineTo(54.196815, -7.3399158);
((GeneralPath)shape).quadTo(54.471657, -7.3399158, 54.622105, -7.482547);
((GeneralPath)shape).quadTo(54.77255, -7.625178, 54.77255, -7.8895984);
((GeneralPath)shape).quadTo(54.77255, -8.151414, 54.622105, -8.294045);
((GeneralPath)shape).quadTo(54.471657, -8.436676, 54.196815, -8.436676);
((GeneralPath)shape).lineTo(53.70054, -8.436676);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(53.305862, -8.761015);
((GeneralPath)shape).lineTo(54.196815, -8.761015);
((GeneralPath)shape).quadTo(54.68658, -8.761015, 54.937977, -8.538927);
((GeneralPath)shape).quadTo(55.189373, -8.31684, 55.189373, -7.8895984);
((GeneralPath)shape).quadTo(55.189373, -7.4571466, 54.937977, -7.236362);
((GeneralPath)shape).quadTo(54.68658, -7.0155773, 54.196815, -7.0155773);
((GeneralPath)shape).lineTo(53.70054, -7.0155773);
((GeneralPath)shape).lineTo(53.70054, -5.8432684);
((GeneralPath)shape).lineTo(53.305862, -5.8432684);
((GeneralPath)shape).lineTo(53.305862, -8.761015);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(55.673275, -8.761015);
((GeneralPath)shape).lineTo(56.070557, -8.761015);
((GeneralPath)shape).lineTo(56.070557, -6.9882236);
((GeneralPath)shape).quadTo(56.070557, -6.5193, 56.24054, -6.3134947);
((GeneralPath)shape).quadTo(56.410526, -6.1076894, 56.790874, -6.1076894);
((GeneralPath)shape).quadTo(57.16992, -6.1076894, 57.339905, -6.3134947);
((GeneralPath)shape).quadTo(57.50989, -6.5193, 57.50989, -6.9882236);
((GeneralPath)shape).lineTo(57.50989, -8.761015);
((GeneralPath)shape).lineTo(57.907173, -8.761015);
((GeneralPath)shape).lineTo(57.907173, -6.940028);
((GeneralPath)shape).quadTo(57.907173, -6.369505, 57.624516, -6.07773);
((GeneralPath)shape).quadTo(57.34186, -5.7859554, 56.790874, -5.7859554);
((GeneralPath)shape).quadTo(56.238586, -5.7859554, 55.95593, -6.07773);
((GeneralPath)shape).quadTo(55.673275, -6.369505, 55.673275, -6.940028);
((GeneralPath)shape).lineTo(55.673275, -8.761015);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(60.395725, -8.664625);
((GeneralPath)shape).lineTo(60.395725, -8.280368);
((GeneralPath)shape).quadTo(60.171684, -8.387178, 59.97239, -8.439933);
((GeneralPath)shape).quadTo(59.773098, -8.492686, 59.58683, -8.492686);
((GeneralPath)shape).quadTo(59.2651, -8.492686, 59.0899, -8.36764);
((GeneralPath)shape).quadTo(58.914707, -8.242594, 58.914707, -8.01204);
((GeneralPath)shape).quadTo(58.914707, -7.81926, 59.03129, -7.720265);
((GeneralPath)shape).quadTo(59.147865, -7.62127, 59.472206, -7.5613523);
((GeneralPath)shape).lineTo(59.710575, -7.5118546);
((GeneralPath)shape).quadTo(60.152145, -7.4284906, 60.36186, -7.216172);
((GeneralPath)shape).quadTo(60.57157, -7.0038543, 60.57157, -6.648254);
((GeneralPath)shape).quadTo(60.57157, -6.2249203, 60.287613, -6.005438);
((GeneralPath)shape).quadTo(60.00365, -5.7859554, 59.453968, -5.7859554);
((GeneralPath)shape).quadTo(59.24686, -5.7859554, 59.013702, -5.833499);
((GeneralPath)shape).quadTo(58.780544, -5.881043, 58.53045, -5.9722223);
((GeneralPath)shape).lineTo(58.53045, -6.3786225);
((GeneralPath)shape).quadTo(58.770123, -6.2444587, 59.000675, -6.176074);
((GeneralPath)shape).quadTo(59.23123, -6.1076894, 59.453968, -6.1076894);
((GeneralPath)shape).quadTo(59.792637, -6.1076894, 59.9763, -6.240551);
((GeneralPath)shape).quadTo(60.15996, -6.3734126, 60.15996, -6.6195974);
((GeneralPath)shape).quadTo(60.15996, -6.834521, 60.02775, -6.9556594);
((GeneralPath)shape).quadTo(59.89554, -7.076798, 59.594646, -7.136716);
((GeneralPath)shape).lineTo(59.354973, -7.183608);
((GeneralPath)shape).quadTo(58.913406, -7.2721825, 58.716064, -7.459752);
((GeneralPath)shape).quadTo(58.518726, -7.647321, 58.518726, -7.980778);
((GeneralPath)shape).quadTo(58.518726, -8.36764, 58.790962, -8.590379);
((GeneralPath)shape).quadTo(59.063198, -8.813117, 59.542545, -8.813117);
((GeneralPath)shape).quadTo(59.747047, -8.813117, 59.960014, -8.775994);
((GeneralPath)shape).quadTo(60.172985, -8.738872, 60.395725, -8.664625);
((GeneralPath)shape).closePath();
paint = getColor(0, 0, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_4;
g.setTransform(defaultTransform__0_0_4);
g.setClip(clip__0_0_4);
float alpha__0_0_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_5 = g.getClip();
AffineTransform defaultTransform__0_0_5 = g.getTransform();
g.transform(new AffineTransform(0.8727239966392517f, 0.4041920006275177f, -0.6862050294876099f, 0.828029990196228f, 0.0f, 0.0f));
// _0_0_5 is TextNode of 'g'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(57.987072, 23.135406);
((GeneralPath)shape).lineTo(44.09652, 23.135406);
((GeneralPath)shape).quadTo(39.323925, 23.135406, 36.20298, 19.96757);
((GeneralPath)shape).quadTo(33.082035, 16.799732, 33.082035, 11.964612);
((GeneralPath)shape).quadTo(33.082035, 7.1607537, 36.17172, 4.2169576);
((GeneralPath)shape).quadTo(39.261402, 1.2731616, 44.09652, 1.2731616);
((GeneralPath)shape).lineTo(56.423992, 1.2731616);
((GeneralPath)shape).lineTo(56.423992, 5.0766506);
((GeneralPath)shape).lineTo(44.09652, 5.0766506);
((GeneralPath)shape).quadTo(40.959946, 5.0766506, 38.927944, 7.0930204);
((GeneralPath)shape).quadTo(36.895943, 9.10939, 36.895943, 12.287648);
((GeneralPath)shape).quadTo(36.895943, 15.424224, 38.927944, 17.37286);
((GeneralPath)shape).quadTo(40.959946, 19.321497, 44.09652, 19.321497);
((GeneralPath)shape).lineTo(54.183582, 19.321497);
((GeneralPath)shape).lineTo(54.183582, 14.559321);
((GeneralPath)shape).lineTo(43.815166, 14.559321);
((GeneralPath)shape).lineTo(43.815166, 11.068448);
((GeneralPath)shape).lineTo(57.987072, 11.068448);
((GeneralPath)shape).lineTo(57.987072, 23.135406);
((GeneralPath)shape).closePath();
paint = getColor(0, 0, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_5;
g.setTransform(defaultTransform__0_0_5);
g.setClip(clip__0_0_5);
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
        return 2;
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
	 * Should this icon be drawn in a disabled state
	 */
	boolean disabled = false;

	/**
	 * Creates a new transcoded SVG image.
	 */
	public CorpusIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public CorpusIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public CorpusIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public CorpusIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public CorpusIcon(int width, int height) {
		this(width, height, false);
	}
	
	public CorpusIcon(int width, int height, boolean disabled) {
		this.width = width;
		this.height = height;
		this.disabled = disabled;
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
		paint(g2d, disabled);
		g2d.dispose();
	}
}

