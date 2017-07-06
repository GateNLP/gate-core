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
public class EditListIcon implements
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
g.transform(new AffineTransform(0.9368699789047241f, 0.34967800974845886f, -0.7519339919090271f, 0.6592379808425903f, 0.0f, 0.0f));
// _0_0_0 is ShapeNode
paint = getColor(255, 252, 252, 255, disabled);
shape = new Rectangle2D.Double(43.53960418701172, 14.807701110839844, 36.443790435791016, 37.355445861816406);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(0.71432495f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(43.53960418701172, 14.807701110839844, 36.443790435791016, 37.355445861816406);
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
g.transform(new AffineTransform(0.9374169707298279f, 0.34821000695228577f, -0.7534970045089722f, 0.6574509739875793f, 0.0f, 0.0f));
// _0_0_1 is ShapeNode
paint = getColor(255, 0, 0, 255, disabled);
shape = new Rectangle2D.Double(48.91783142089844, 25.05886459350586, 3.9589712619781494, 3.2136175632476807);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_1;
g.setTransform(defaultTransform__0_0_1);
g.setClip(clip__0_0_1);
float alpha__0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(0.9374169707298279f, 0.34821000695228577f, -0.7534970045089722f, 0.6574509739875793f, 0.0f, 0.0f));
// _0_0_2 is ShapeNode
paint = getColor(255, 0, 0, 255, disabled);
shape = new Rectangle2D.Double(48.593910217285156, 18.631616592407227, 3.9589712619781494, 3.2136175632476807);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2;
g.setTransform(defaultTransform__0_0_2);
g.setClip(clip__0_0_2);
float alpha__0_0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3 = g.getClip();
AffineTransform defaultTransform__0_0_3 = g.getTransform();
g.transform(new AffineTransform(0.9374169707298279f, 0.34821000695228577f, -0.7534970045089722f, 0.6574509739875793f, 0.0f, 0.0f));
// _0_0_3 is ShapeNode
paint = getColor(255, 0, 0, 255, disabled);
shape = new Rectangle2D.Double(48.917842864990234, 31.486116409301758, 3.9589712619781494, 3.2136175632476807);
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
g.transform(new AffineTransform(0.9374169707298279f, 0.34821000695228577f, -0.7534970045089722f, 0.6574509739875793f, 0.0f, 0.0f));
// _0_0_4 is ShapeNode
paint = getColor(255, 0, 0, 255, disabled);
shape = new Rectangle2D.Double(48.917842864990234, 37.913352966308594, 3.9589712619781494, 3.2136175632476807);
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
g.transform(new AffineTransform(1.1099679470062256f, 0.41230401396751404f, -0.7242230176925659f, 0.6319100260734558f, 0.0f, 0.0f));
// _0_0_5 is TextNode of 'edit'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(49.254467, 21.496637);
((GeneralPath)shape).lineTo(49.254467, 21.7905);
((GeneralPath)shape).lineTo(46.492153, 21.7905);
((GeneralPath)shape).quadTo(46.531334, 22.41088, 46.865467, 22.735216);
((GeneralPath)shape).quadTo(47.1996, 23.059555, 47.79821, 23.059555);
((GeneralPath)shape).quadTo(48.144318, 23.059555, 48.468655, 22.97466);
((GeneralPath)shape).quadTo(48.792995, 22.889767, 49.11298, 22.719978);
((GeneralPath)shape).lineTo(49.11298, 23.288115);
((GeneralPath)shape).quadTo(48.790817, 23.425251, 48.45124, 23.498173);
((GeneralPath)shape).quadTo(48.111668, 23.571095, 47.761208, 23.571095);
((GeneralPath)shape).quadTo(46.886147, 23.571095, 46.375694, 23.060642);
((GeneralPath)shape).quadTo(45.865242, 22.550192, 45.865242, 21.681662);
((GeneralPath)shape).quadTo(45.865242, 20.784834, 46.349575, 20.25697);
((GeneralPath)shape).quadTo(46.833904, 19.729103, 47.656723, 19.729103);
((GeneralPath)shape).quadTo(48.394646, 19.729103, 48.82456, 20.204727);
((GeneralPath)shape).quadTo(49.254467, 20.68035, 49.254467, 21.496637);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(48.65368, 21.320318);
((GeneralPath)shape).quadTo(48.647152, 20.826193, 48.37723, 20.53233);
((GeneralPath)shape).quadTo(48.10731, 20.238466, 47.663254, 20.238466);
((GeneralPath)shape).quadTo(47.16042, 20.238466, 46.858936, 20.522533);
((GeneralPath)shape).quadTo(46.557457, 20.806602, 46.51174, 21.322496);
((GeneralPath)shape).lineTo(48.65368, 21.320318);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(52.645866, 20.373426);
((GeneralPath)shape).lineTo(52.645866, 18.394745);
((GeneralPath)shape).lineTo(53.246655, 18.394745);
((GeneralPath)shape).lineTo(53.246655, 23.475317);
((GeneralPath)shape).lineTo(52.645866, 23.475317);
((GeneralPath)shape).lineTo(52.645866, 22.926771);
((GeneralPath)shape).quadTo(52.45649, 23.253286, 52.168068, 23.412191);
((GeneralPath)shape).quadTo(51.879646, 23.571095, 51.47477, 23.571095);
((GeneralPath)shape).quadTo(50.810856, 23.571095, 50.395092, 23.041052);
((GeneralPath)shape).quadTo(49.97933, 22.51101, 49.97933, 21.64901);
((GeneralPath)shape).quadTo(49.97933, 20.787012, 50.395092, 20.258057);
((GeneralPath)shape).quadTo(50.810856, 19.729103, 51.47477, 19.729103);
((GeneralPath)shape).quadTo(51.879646, 19.729103, 52.168068, 19.888006);
((GeneralPath)shape).quadTo(52.45649, 20.046911, 52.645866, 20.373426);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(50.59971, 21.64901);
((GeneralPath)shape).quadTo(50.59971, 22.312923, 50.871803, 22.689505);
((GeneralPath)shape).quadTo(51.143898, 23.066086, 51.620613, 23.066086);
((GeneralPath)shape).quadTo(52.097324, 23.066086, 52.371597, 22.689505);
((GeneralPath)shape).quadTo(52.645866, 22.312923, 52.645866, 21.64901);
((GeneralPath)shape).quadTo(52.645866, 20.987274, 52.371597, 20.609604);
((GeneralPath)shape).quadTo(52.097324, 20.231936, 51.620613, 20.231936);
((GeneralPath)shape).quadTo(51.143898, 20.231936, 50.871803, 20.609604);
((GeneralPath)shape).quadTo(50.59971, 20.987274, 50.59971, 21.64901);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(54.485237, 19.81835);
((GeneralPath)shape).lineTo(55.08602, 19.81835);
((GeneralPath)shape).lineTo(55.08602, 23.475317);
((GeneralPath)shape).lineTo(54.485237, 23.475317);
((GeneralPath)shape).lineTo(54.485237, 19.81835);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(54.485237, 18.394745);
((GeneralPath)shape).lineTo(55.08602, 18.394745);
((GeneralPath)shape).lineTo(55.08602, 19.154436);
((GeneralPath)shape).lineTo(54.485237, 19.154436);
((GeneralPath)shape).lineTo(54.485237, 18.394745);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(56.937363, 18.780033);
((GeneralPath)shape).lineTo(56.937363, 19.81835);
((GeneralPath)shape).lineTo(58.173767, 19.81835);
((GeneralPath)shape).lineTo(58.173767, 20.284178);
((GeneralPath)shape).lineTo(56.937363, 20.284178);
((GeneralPath)shape).lineTo(56.937363, 22.269388);
((GeneralPath)shape).quadTo(56.937363, 22.717802, 57.059265, 22.845142);
((GeneralPath)shape).quadTo(57.181164, 22.972485, 57.557743, 22.972485);
((GeneralPath)shape).lineTo(58.173767, 22.972485);
((GeneralPath)shape).lineTo(58.173767, 23.475317);
((GeneralPath)shape).lineTo(57.557743, 23.475317);
((GeneralPath)shape).quadTo(56.86118, 23.475317, 56.596703, 23.215193);
((GeneralPath)shape).quadTo(56.332222, 22.95507, 56.332222, 22.269388);
((GeneralPath)shape).lineTo(56.332222, 20.284178);
((GeneralPath)shape).lineTo(55.892517, 20.284178);
((GeneralPath)shape).lineTo(55.892517, 19.81835);
((GeneralPath)shape).lineTo(56.332222, 19.81835);
((GeneralPath)shape).lineTo(56.332222, 18.780033);
((GeneralPath)shape).lineTo(56.937363, 18.780033);
((GeneralPath)shape).closePath();
paint = getColor(0, 0, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_5;
g.setTransform(defaultTransform__0_0_5);
g.setClip(clip__0_0_5);
float alpha__0_0_6 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_6 = g.getClip();
AffineTransform defaultTransform__0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.1099679470062256f, 0.41230401396751404f, -0.7242230176925659f, 0.6319100260734558f, 0.0f, 0.0f));
// _0_0_6 is TextNode of 'list'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(46.9976, 32.090084);
((GeneralPath)shape).lineTo(47.598385, 32.090084);
((GeneralPath)shape).lineTo(47.598385, 37.170654);
((GeneralPath)shape).lineTo(46.9976, 37.170654);
((GeneralPath)shape).lineTo(46.9976, 32.090084);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(48.85547, 33.513687);
((GeneralPath)shape).lineTo(49.456253, 33.513687);
((GeneralPath)shape).lineTo(49.456253, 37.170654);
((GeneralPath)shape).lineTo(48.85547, 37.170654);
((GeneralPath)shape).lineTo(48.85547, 33.513687);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(48.85547, 32.090084);
((GeneralPath)shape).lineTo(49.456253, 32.090084);
((GeneralPath)shape).lineTo(49.456253, 32.849773);
((GeneralPath)shape).lineTo(48.85547, 32.849773);
((GeneralPath)shape).lineTo(48.85547, 32.090084);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(53.044655, 33.62035);
((GeneralPath)shape).lineTo(53.044655, 34.188484);
((GeneralPath)shape).quadTo(52.789974, 34.05788, 52.5157, 33.992577);
((GeneralPath)shape).quadTo(52.24143, 33.927273, 51.947567, 33.927273);
((GeneralPath)shape).quadTo(51.499153, 33.927273, 51.27603, 34.06441);
((GeneralPath)shape).quadTo(51.052914, 34.201546, 51.052914, 34.47582);
((GeneralPath)shape).quadTo(51.052914, 34.684788, 51.212906, 34.80451);
((GeneralPath)shape).quadTo(51.3729, 34.924232, 51.85614, 35.030895);
((GeneralPath)shape).lineTo(52.060757, 35.076607);
((GeneralPath)shape).quadTo(52.700726, 35.21374, 52.970646, 35.46407);
((GeneralPath)shape).quadTo(53.240562, 35.714397, 53.240562, 36.160633);
((GeneralPath)shape).quadTo(53.240562, 36.67, 52.836773, 36.968216);
((GeneralPath)shape).quadTo(52.432983, 37.266434, 51.72771, 37.266434);
((GeneralPath)shape).quadTo(51.43385, 37.266434, 51.11604, 37.208748);
((GeneralPath)shape).quadTo(50.798233, 37.151062, 50.4456, 37.035694);
((GeneralPath)shape).lineTo(50.4456, 36.415318);
((GeneralPath)shape).quadTo(50.77864, 36.58946, 51.10189, 36.67544);
((GeneralPath)shape).quadTo(51.42514, 36.76142, 51.740772, 36.76142);
((GeneralPath)shape).quadTo(52.16524, 36.76142, 52.393803, 36.61667);
((GeneralPath)shape).quadTo(52.622364, 36.471912, 52.622364, 36.20635);
((GeneralPath)shape).quadTo(52.622364, 35.96255, 52.458015, 35.831944);
((GeneralPath)shape).quadTo(52.29367, 35.701336, 51.73424, 35.579437);
((GeneralPath)shape).lineTo(51.525272, 35.53155);
((GeneralPath)shape).quadTo(50.96802, 35.414005, 50.719868, 35.170208);
((GeneralPath)shape).quadTo(50.471718, 34.926407, 50.471718, 34.501938);
((GeneralPath)shape).quadTo(50.471718, 33.986046, 50.837414, 33.705242);
((GeneralPath)shape).quadTo(51.20311, 33.42444, 51.875732, 33.42444);
((GeneralPath)shape).quadTo(52.20878, 33.42444, 52.50264, 33.47342);
((GeneralPath)shape).quadTo(52.796505, 33.522396, 53.044655, 33.62035);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(54.791508, 32.475372);
((GeneralPath)shape).lineTo(54.791508, 33.513687);
((GeneralPath)shape).lineTo(56.027912, 33.513687);
((GeneralPath)shape).lineTo(56.027912, 33.979515);
((GeneralPath)shape).lineTo(54.791508, 33.979515);
((GeneralPath)shape).lineTo(54.791508, 35.964725);
((GeneralPath)shape).quadTo(54.791508, 36.41314, 54.91341, 36.54048);
((GeneralPath)shape).quadTo(55.03531, 36.66782, 55.41189, 36.66782);
((GeneralPath)shape).lineTo(56.027912, 36.66782);
((GeneralPath)shape).lineTo(56.027912, 37.170654);
((GeneralPath)shape).lineTo(55.41189, 37.170654);
((GeneralPath)shape).quadTo(54.715324, 37.170654, 54.450848, 36.91053);
((GeneralPath)shape).quadTo(54.186367, 36.650406, 54.186367, 35.964725);
((GeneralPath)shape).lineTo(54.186367, 33.979515);
((GeneralPath)shape).lineTo(53.746662, 33.979515);
((GeneralPath)shape).lineTo(53.746662, 33.513687);
((GeneralPath)shape).lineTo(54.186367, 33.513687);
((GeneralPath)shape).lineTo(54.186367, 32.475372);
((GeneralPath)shape).lineTo(54.791508, 32.475372);
((GeneralPath)shape).closePath();
paint = getColor(0, 0, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_6;
g.setTransform(defaultTransform__0_0_6);
g.setClip(clip__0_0_6);
float alpha__0_0_7 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7 = g.getClip();
AffineTransform defaultTransform__0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.2776210308074951f, 0.0f, 0.0f, 1.1301720142364502f, 0.482571005821228f, 3.13017201423645f));
// _0_0_7 is CompositeGraphicsNode
float alpha__0_0_7_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7_0 = g.getClip();
AffineTransform defaultTransform__0_0_7_0 = g.getTransform();
g.transform(new AffineTransform(1.0475900173187256f, 0.0f, 0.0f, 1.0812699794769287f, -0.9781540036201477f, -3.4401419162750244f));
// _0_0_7_0 is CompositeGraphicsNode
float alpha__0_0_7_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_7_0_0 = g.getTransform();
g.transform(new AffineTransform(0.5968350172042847f, -0.9031320214271545f, 1.9193710088729858f, 1.9855509996414185f, 1.8936059474945068f, 9.649788856506348f));
// _0_0_7_0_0 is ShapeNode
paint = getColor(15, 15, 15, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(8.979581, 1.3149831);
((GeneralPath)shape).curveTo(8.979581, 2.418983, 7.0545454, 3.090909, 4.5454545, 3.090909);
((GeneralPath)shape).curveTo(2.0363636, 3.090909, 0.10308257, 1.7839432, 0.10308257, 0.6799432);
((GeneralPath)shape).curveTo(0.10308257, -0.42405677, 2.0363636, -0.9090909, 4.5454545, -0.9090909);
((GeneralPath)shape).curveTo(7.0545454, -0.9090909, 8.979581, 0.21098308, 8.979581, 1.3149831);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_7_0_0;
g.setTransform(defaultTransform__0_0_7_0_0);
g.setClip(clip__0_0_7_0_0);
float alpha__0_0_7_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_7_0_1 = g.getTransform();
g.transform(new AffineTransform(0.9515249729156494f, -0.30757200717926025f, 0.30757200717926025f, 0.9515249729156494f, 0.0f, 0.0f));
// _0_0_7_0_1 is ShapeNode
paint = getColor(255, 0, 0, 255, disabled);
shape = new Rectangle2D.Double(-1.2299132347106934, 8.873310089111328, 10.0, 30.454545974731445);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_7_0_1;
g.setTransform(defaultTransform__0_0_7_0_1);
g.setClip(clip__0_0_7_0_1);
float alpha__0_0_7_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_7_0_2 = g.getTransform();
g.transform(new AffineTransform(0.8320909738540649f, 0.5546389818191528f, -0.4608420133590698f, 0.8874819874763489f, 0.0f, 0.0f));
// _0_0_7_0_2 is ShapeNode
paint = getColor(248, 150, 57, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(28.931767, 20.331942);
((GeneralPath)shape).lineTo(33.922745, 17.49089);
((GeneralPath)shape).lineTo(35.06526, 25.782244);
((GeneralPath)shape).lineTo(27.077396, 25.579353);
((GeneralPath)shape).lineTo(28.931767, 20.331942);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_7_0_2;
g.setTransform(defaultTransform__0_0_7_0_2);
g.setClip(clip__0_0_7_0_2);
float alpha__0_0_7_0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7_0_3 = g.getClip();
AffineTransform defaultTransform__0_0_7_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7_0_3 is ShapeNode
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(6.0, 7.0);
((GeneralPath)shape).curveTo(15.0, 34.0, 15.0, 34.0, 15.0, 34.0);
((GeneralPath)shape).lineTo(15.0, 34.0);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_7_0_3;
g.setTransform(defaultTransform__0_0_7_0_3);
g.setClip(clip__0_0_7_0_3);
float alpha__0_0_7_0_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7_0_4 = g.getClip();
AffineTransform defaultTransform__0_0_7_0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7_0_4 is ShapeNode
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.0621475f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(1.9822894, 8.535675);
((GeneralPath)shape).curveTo(11.451869, 37.485455, 11.451869, 37.485455, 11.451869, 37.485455);
((GeneralPath)shape).lineTo(11.451869, 37.485455);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_7_0_4;
g.setTransform(defaultTransform__0_0_7_0_4);
g.setClip(clip__0_0_7_0_4);
float alpha__0_0_7_0_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7_0_5 = g.getClip();
AffineTransform defaultTransform__0_0_7_0_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7_0_5 is ShapeNode
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.062861f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(10.531432, 5.545107);
((GeneralPath)shape).curveTo(20.02234, 34.468643, 20.02234, 34.468643, 20.02234, 34.468643);
((GeneralPath)shape).lineTo(20.02234, 34.468643);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_7_0_5;
g.setTransform(defaultTransform__0_0_7_0_5);
g.setClip(clip__0_0_7_0_5);
origAlpha = alpha__0_0_7_0;
g.setTransform(defaultTransform__0_0_7_0);
g.setClip(clip__0_0_7_0);
float alpha__0_0_7_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7_1 = g.getClip();
AffineTransform defaultTransform__0_0_7_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7_1 is ShapeNode
paint = getColor(5, 5, 5, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.90909, 39.727272);
((GeneralPath)shape).lineTo(18.0, 40.272728);
((GeneralPath)shape).lineTo(17.636362, 42.90909);
((GeneralPath)shape).lineTo(15.636363, 40.90909);
((GeneralPath)shape).lineTo(16.90909, 39.727272);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_7_1;
g.setTransform(defaultTransform__0_0_7_1);
g.setClip(clip__0_0_7_1);
origAlpha = alpha__0_0_7;
g.setTransform(defaultTransform__0_0_7);
g.setClip(clip__0_0_7);
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
	 * Should this icon be drawn in a disabled state
	 */
	boolean disabled = false;

	/**
	 * Creates a new transcoded SVG image.
	 */
	public EditListIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public EditListIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public EditListIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public EditListIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public EditListIcon(int width, int height) {
		this(width, height, false);
	}
	
	public EditListIcon(int width, int height, boolean disabled) {
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

