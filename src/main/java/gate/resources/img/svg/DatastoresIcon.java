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
public class DatastoresIcon implements
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0 is ShapeNode
paint = getColor(206, 38, 38, 255, disabled);
shape = new Rectangle2D.Double(11.680727005004883, 8.487349510192871, 50.461463928222656, 40.4879264831543);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.2686436f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(11.680727005004883, 8.487349510192871, 50.461463928222656, 40.4879264831543);
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1 is ShapeNode
paint = getColor(106, 18, 18, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(2.9193578, 61.39315);
((GeneralPath)shape).lineTo(1.9913672, 18.079502);
((GeneralPath)shape).lineTo(10.537846, 9.464771);
((GeneralPath)shape).lineTo(11.678875, 49.533157);
((GeneralPath)shape).lineTo(2.9193578, 61.39315);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.2725273f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(2.9193578, 61.39315);
((GeneralPath)shape).lineTo(1.9913672, 18.079502);
((GeneralPath)shape).lineTo(10.537846, 9.464771);
((GeneralPath)shape).lineTo(11.678875, 49.533157);
((GeneralPath)shape).lineTo(2.9193578, 61.39315);
((GeneralPath)shape).closePath();
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2 is ShapeNode
paint = getColor(106, 18, 18, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(51.897774, 18.104786);
((GeneralPath)shape).lineTo(61.99943, 9.241092);
((GeneralPath)shape).lineTo(62.244495, 48.501884);
((GeneralPath)shape).lineTo(51.988857, 61.31634);
((GeneralPath)shape).lineTo(51.897774, 18.104786);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.3573996f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(51.897774, 18.104786);
((GeneralPath)shape).lineTo(61.99943, 9.241092);
((GeneralPath)shape).lineTo(62.244495, 48.501884);
((GeneralPath)shape).lineTo(51.988857, 61.31634);
((GeneralPath)shape).lineTo(51.897774, 18.104786);
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(44.45938491821289, 12.750892639160156), new Point2D.Double(42.84593963623047, 6.251910209655762), new float[] {0.0f,1.0f}, new Color[] {getColor(255, 0, 0, 255, disabled),getColor(255, 0, 0, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.0f, -2.2460811138153076f, 1.6936860084533691f, 0.0f, -9.348050117492676f, 93.51268005371094f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(2.2226298, 61.135593);
((GeneralPath)shape).lineTo(2.042529, 19.113161);
((GeneralPath)shape).lineTo(10.698254, 10.283418);
((GeneralPath)shape).lineTo(11.231442, 49.921207);
((GeneralPath)shape).lineTo(2.2226298, 61.135593);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.2725273f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(2.2226298, 61.135593);
((GeneralPath)shape).lineTo(2.042529, 19.113161);
((GeneralPath)shape).lineTo(10.698254, 10.283418);
((GeneralPath)shape).lineTo(11.231442, 49.921207);
((GeneralPath)shape).lineTo(2.2226298, 61.135593);
((GeneralPath)shape).closePath();
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_4 is ShapeNode
paint = getColor(106, 18, 18, 255, disabled);
shape = new Rectangle2D.Double(2.4696054458618164, 18.781858444213867, 47.539310455322266, 42.732086181640625);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.2650293f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(2.4696054458618164, 18.781858444213867, 47.539310455322266, 42.732086181640625);
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
g.transform(new AffineTransform(0.8658620119094849f, 0.0f, 0.0f, 0.9704470038414001f, -41.418609619140625f, 25.132570266723633f));
// _0_0_5 is CompositeGraphicsNode
float alpha__0_0_5_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_5_0 = g.getClip();
AffineTransform defaultTransform__0_0_5_0 = g.getTransform();
g.transform(new AffineTransform(0.9105449914932251f, 0.4134100079536438f, -0.6455870270729065f, 0.763687014579773f, 0.0f, 0.0f));
// _0_0_5_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(42.18181610107422, 29.27272605895996), new Point2D.Double(10.363636016845703, 60.54545211791992), new float[] {0.0f,1.0f}, new Color[] {getColor(255, 255, 255, 255, disabled),getColor(6, 1, 1, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9779120087623596f, -2.3235840274082875E-7f, -3.2074518685476505E-7f, 1.084488034248352f, 35.376800537109375f, -70.60813903808594f));
shape = new Rectangle2D.Double(50.34498596191406, -58.5313720703125, 33.21522903442383, 47.52131652832031);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.476998f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(50.34498596191406, -58.5313720703125, 33.21522903442383, 47.52131652832031);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_5_0;
g.setTransform(defaultTransform__0_0_5_0);
g.setClip(clip__0_0_5_0);
float alpha__0_0_5_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_5_1 = g.getClip();
AffineTransform defaultTransform__0_0_5_1 = g.getTransform();
g.transform(new AffineTransform(0.8727239966392517f, 0.4041920006275177f, -0.6862050294876099f, 0.828029990196228f, 0.0f, 0.0f));
// _0_0_5_1 is TextNode of 'GATE DOC'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(60.526543, -48.1058);
((GeneralPath)shape).lineTo(60.526543, -48.88864);
((GeneralPath)shape).lineTo(59.881775, -48.88864);
((GeneralPath)shape).lineTo(59.881775, -49.212982);
((GeneralPath)shape).lineTo(60.917313, -49.212982);
((GeneralPath)shape).lineTo(60.917313, -47.961216);
((GeneralPath)shape).quadTo(60.68806, -47.798397, 60.41257, -47.71503);
((GeneralPath)shape).quadTo(60.137077, -47.631668, 59.824463, -47.631668);
((GeneralPath)shape).quadTo(59.140617, -47.631668, 58.75506, -48.031555);
((GeneralPath)shape).quadTo(58.369495, -48.431442, 58.369495, -49.14525);
((GeneralPath)shape).quadTo(58.369495, -49.860355, 58.75506, -50.25959);
((GeneralPath)shape).quadTo(59.140617, -50.65883, 59.824463, -50.65883);
((GeneralPath)shape).quadTo(60.109722, -50.65883, 60.36698, -50.58849);
((GeneralPath)shape).quadTo(60.624237, -50.51815, 60.84046, -50.381382);
((GeneralPath)shape).lineTo(60.84046, -49.961956);
((GeneralPath)shape).quadTo(60.62163, -50.14692, 60.375446, -50.240704);
((GeneralPath)shape).quadTo(60.12926, -50.334488, 59.85833, -50.334488);
((GeneralPath)shape).quadTo(59.322975, -50.334488, 59.053993, -50.03555);
((GeneralPath)shape).quadTo(58.785015, -49.73661, 58.785015, -49.14525);
((GeneralPath)shape).quadTo(58.785015, -48.555187, 59.053993, -48.25625);
((GeneralPath)shape).quadTo(59.322975, -47.95731, 59.85833, -47.95731);
((GeneralPath)shape).quadTo(60.06674, -47.95731, 60.23086, -47.99313);
((GeneralPath)shape).quadTo(60.394985, -48.02895, 60.526543, -48.1058);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(62.6126, -50.21726);
((GeneralPath)shape).lineTo(62.077248, -48.7662);
((GeneralPath)shape).lineTo(63.15056, -48.7662);
((GeneralPath)shape).lineTo(62.6126, -50.21726);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(62.389862, -50.606724);
((GeneralPath)shape).lineTo(62.837944, -50.606724);
((GeneralPath)shape).lineTo(63.94903, -47.68898);
((GeneralPath)shape).lineTo(63.538723, -47.68898);
((GeneralPath)shape).lineTo(63.273, -48.437954);
((GeneralPath)shape).lineTo(61.958714, -48.437954);
((GeneralPath)shape).lineTo(61.69299, -47.68898);
((GeneralPath)shape).lineTo(61.27617, -47.68898);
((GeneralPath)shape).lineTo(62.389862, -50.606724);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(63.970524, -50.606724);
((GeneralPath)shape).lineTo(66.43888, -50.606724);
((GeneralPath)shape).lineTo(66.43888, -50.27457);
((GeneralPath)shape).lineTo(65.40334, -50.27457);
((GeneralPath)shape).lineTo(65.40334, -47.68898);
((GeneralPath)shape).lineTo(65.006065, -47.68898);
((GeneralPath)shape).lineTo(65.006065, -50.27457);
((GeneralPath)shape).lineTo(63.970524, -50.27457);
((GeneralPath)shape).lineTo(63.970524, -50.606724);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(66.819885, -50.606724);
((GeneralPath)shape).lineTo(68.66432, -50.606724);
((GeneralPath)shape).lineTo(68.66432, -50.27457);
((GeneralPath)shape).lineTo(67.21456, -50.27457);
((GeneralPath)shape).lineTo(67.21456, -49.41097);
((GeneralPath)shape).lineTo(68.603096, -49.41097);
((GeneralPath)shape).lineTo(68.603096, -49.078815);
((GeneralPath)shape).lineTo(67.21456, -49.078815);
((GeneralPath)shape).lineTo(67.21456, -48.021133);
((GeneralPath)shape).lineTo(68.699486, -48.021133);
((GeneralPath)shape).lineTo(68.699486, -47.68898);
((GeneralPath)shape).lineTo(66.819885, -47.68898);
((GeneralPath)shape).lineTo(66.819885, -50.606724);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(71.01479, -50.282387);
((GeneralPath)shape).lineTo(71.01479, -48.013317);
((GeneralPath)shape).lineTo(71.49154, -48.013317);
((GeneralPath)shape).quadTo(72.09462, -48.013317, 72.37533, -48.286858);
((GeneralPath)shape).quadTo(72.65603, -48.560394, 72.65603, -49.15046);
((GeneralPath)shape).quadTo(72.65603, -49.73661, 72.37533, -50.0095);
((GeneralPath)shape).quadTo(72.09462, -50.282387, 71.49154, -50.282387);
((GeneralPath)shape).lineTo(71.01479, -50.282387);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(70.62012, -50.606724);
((GeneralPath)shape).lineTo(71.43031, -50.606724);
((GeneralPath)shape).quadTo(72.27828, -50.606724, 72.67492, -50.25373);
((GeneralPath)shape).quadTo(73.07155, -49.900734, 73.07155, -49.15046);
((GeneralPath)shape).quadTo(73.07155, -48.396275, 72.67296, -48.042625);
((GeneralPath)shape).quadTo(72.274376, -47.68898, 71.43031, -47.68898);
((GeneralPath)shape).lineTo(70.62012, -47.68898);
((GeneralPath)shape).lineTo(70.62012, -50.606724);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(74.88537, -50.338398);
((GeneralPath)shape).quadTo(74.45552, -50.338398, 74.20217, -50.017967);
((GeneralPath)shape).quadTo(73.94882, -49.697536, 73.94882, -49.14525);
((GeneralPath)shape).quadTo(73.94882, -48.59426, 74.20217, -48.27383);
((GeneralPath)shape).quadTo(74.45552, -47.9534, 74.88537, -47.9534);
((GeneralPath)shape).quadTo(75.315216, -47.9534, 75.565956, -48.27383);
((GeneralPath)shape).quadTo(75.8167, -48.59426, 75.8167, -49.14525);
((GeneralPath)shape).quadTo(75.8167, -49.697536, 75.565956, -50.017967);
((GeneralPath)shape).quadTo(75.315216, -50.338398, 74.88537, -50.338398);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(74.88537, -50.65883);
((GeneralPath)shape).quadTo(75.49887, -50.65883, 75.866196, -50.247868);
((GeneralPath)shape).quadTo(76.23352, -49.83691, 76.23352, -49.14525);
((GeneralPath)shape).quadTo(76.23352, -48.454887, 75.866196, -48.043278);
((GeneralPath)shape).quadTo(75.49887, -47.631668, 74.88537, -47.631668);
((GeneralPath)shape).quadTo(74.26926, -47.631668, 73.901276, -48.042625);
((GeneralPath)shape).quadTo(73.5333, -48.453587, 73.5333, -49.14525);
((GeneralPath)shape).quadTo(73.5333, -49.83691, 73.901276, -50.247868);
((GeneralPath)shape).quadTo(74.26926, -50.65883, 74.88537, -50.65883);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(79.03534, -50.381382);
((GeneralPath)shape).lineTo(79.03534, -49.965862);
((GeneralPath)shape).quadTo(78.83605, -50.150826, 78.610054, -50.242657);
((GeneralPath)shape).quadTo(78.384056, -50.334488, 78.13006, -50.334488);
((GeneralPath)shape).quadTo(77.629875, -50.334488, 77.36415, -50.029037);
((GeneralPath)shape).quadTo(77.09843, -49.723587, 77.09843, -49.14525);
((GeneralPath)shape).quadTo(77.09843, -48.56821, 77.36415, -48.26276);
((GeneralPath)shape).quadTo(77.629875, -47.95731, 78.13006, -47.95731);
((GeneralPath)shape).quadTo(78.384056, -47.95731, 78.610054, -48.04914);
((GeneralPath)shape).quadTo(78.83605, -48.14097, 79.03534, -48.325935);
((GeneralPath)shape).lineTo(79.03534, -47.91432);
((GeneralPath)shape).quadTo(78.82823, -47.773647, 78.596375, -47.702656);
((GeneralPath)shape).quadTo(78.36452, -47.631668, 78.10661, -47.631668);
((GeneralPath)shape).quadTo(77.44491, -47.631668, 77.06391, -48.037415);
((GeneralPath)shape).quadTo(76.68291, -48.443165, 76.68291, -49.14525);
((GeneralPath)shape).quadTo(76.68291, -49.848633, 77.06391, -50.25373);
((GeneralPath)shape).quadTo(77.44491, -50.65883, 78.10661, -50.65883);
((GeneralPath)shape).quadTo(78.36843, -50.65883, 78.60028, -50.58979);
((GeneralPath)shape).quadTo(78.83214, -50.520756, 79.03534, -50.381382);
((GeneralPath)shape).closePath();
paint = getColor(0, 0, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_5_1;
g.setTransform(defaultTransform__0_0_5_1);
g.setClip(clip__0_0_5_1);
float alpha__0_0_5_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_5_2 = g.getClip();
AffineTransform defaultTransform__0_0_5_2 = g.getTransform();
g.transform(new AffineTransform(0.8727239966392517f, 0.4041920006275177f, -0.6862050294876099f, 0.828029990196228f, 0.0f, 0.0f));
// _0_0_5_2 is TextNode of 'g'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(82.26877, -19.647367);
((GeneralPath)shape).lineTo(68.37821, -19.647367);
((GeneralPath)shape).quadTo(63.605618, -19.647367, 60.484673, -22.815205);
((GeneralPath)shape).quadTo(57.363728, -25.983042, 57.363728, -30.818161);
((GeneralPath)shape).quadTo(57.363728, -35.62202, 60.45341, -38.565815);
((GeneralPath)shape).quadTo(63.543095, -41.509613, 68.37821, -41.509613);
((GeneralPath)shape).lineTo(80.70569, -41.509613);
((GeneralPath)shape).lineTo(80.70569, -37.706123);
((GeneralPath)shape).lineTo(68.37821, -37.706123);
((GeneralPath)shape).quadTo(65.24164, -37.706123, 63.20964, -35.689754);
((GeneralPath)shape).quadTo(61.17764, -33.67338, 61.17764, -30.495125);
((GeneralPath)shape).quadTo(61.17764, -27.358551, 63.20964, -25.409914);
((GeneralPath)shape).quadTo(65.24164, -23.461277, 68.37821, -23.461277);
((GeneralPath)shape).lineTo(78.46528, -23.461277);
((GeneralPath)shape).lineTo(78.46528, -28.223454);
((GeneralPath)shape).lineTo(68.09686, -28.223454);
((GeneralPath)shape).lineTo(68.09686, -31.714327);
((GeneralPath)shape).lineTo(82.26877, -31.714327);
((GeneralPath)shape).lineTo(82.26877, -19.647367);
((GeneralPath)shape).closePath();
paint = getColor(0, 0, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_5_2;
g.setTransform(defaultTransform__0_0_5_2);
g.setClip(clip__0_0_5_2);
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
paint = new LinearGradientPaint(new Point2D.Double(62.028011322021484, 15.417367935180664), new Point2D.Double(17.210084915161133, 54.31932830810547), new float[] {0.0f,1.0f}, new Color[] {getColor(255, 0, 0, 255, disabled),getColor(255, 0, 0, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.7346140146255493f, 0.0f, 0.0f, 2.1930859088897705f, -24.053869247436523f, -60.008888244628906f));
shape = new Rectangle2D.Double(2.0611460208892822, 18.535362243652344, 49.24005889892578, 43.4173583984375);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.9163371f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(2.0611460208892822, 18.535362243652344, 49.24005889892578, 43.4173583984375);
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
paint = getColor(106, 18, 18, 255, disabled);
shape = new Rectangle2D.Double(16.501508712768555, 42.56937026977539, 14.292645454406738, 5.004083633422852);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.9757789f,0,1,4.0f,null,0.0f);
shape = new Rectangle2D.Double(16.501508712768555, 42.56937026977539, 14.292645454406738, 5.004083633422852);
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
paint = new LinearGradientPaint(new Point2D.Double(41.77030944824219, 10.756301879882812), new Point2D.Double(54.14005661010742, 13.624650001525879), new float[] {0.0f,1.0f}, new Color[] {getColor(255, 0, 0, 255, disabled),getColor(255, 0, 0, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.4447300434112549f, 0.0f, 0.0f, 0.8114460110664368f, -12.494319915771484f, 8.739872932434082f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(51.95143, 18.942125);
((GeneralPath)shape).lineTo(62.092213, 9.502001);
((GeneralPath)shape).lineTo(62.003212, 50.15561);
((GeneralPath)shape).lineTo(52.065056, 61.739227);
((GeneralPath)shape).lineTo(51.95143, 18.942125);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.082737f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(51.95143, 18.942125);
((GeneralPath)shape).lineTo(62.092213, 9.502001);
((GeneralPath)shape).lineTo(62.003212, 50.15561);
((GeneralPath)shape).lineTo(52.065056, 61.739227);
((GeneralPath)shape).lineTo(51.95143, 18.942125);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_8;
g.setTransform(defaultTransform__0_0_8);
g.setClip(clip__0_0_8);
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
        return 2;
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
	public DatastoresIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public DatastoresIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public DatastoresIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public DatastoresIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public DatastoresIcon(int width, int height) {
		this(width, height, false);
	}
	
	public DatastoresIcon(int width, int height, boolean disabled) {
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

