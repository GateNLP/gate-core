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
public class MavenIcon implements
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -0.0f, -0.0f));
clip = new Area(g.getClip());
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,64.0,64.0)));
g.setClip(clip);
// _0 is CompositeGraphicsNode
float alpha__0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0 = g.getClip();
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(0.7009001970291138f, 0.0f, 0.0f, 0.7009001970291138f, -96.63282775878906f, -4.752459526062012f));
// _0_0 is CompositeGraphicsNode
float alpha__0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(-7708.796875, -803.360107421875), new Point2D.Double(-7633.15283203125, -714.9074096679688), new float[] {0.0f,0.3123f,0.8383f}, new Color[] {getColor(246, 153, 35, 255, disabled),getColor(247, 154, 35, 255, disabled),getColor(233, 120, 38, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.023193469271063805f, -0.11631035059690475f, 0.11631035059690475f, -0.023193469271063805f, 74.81830596923828f, -889.8349609375f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(159.65108, 19.49864);
((GeneralPath)shape).curveTo(159.1988, 20.566177, 158.84267, 22.887215, 158.68004, 25.993073);
((GeneralPath)shape).lineTo(162.17099, 27.68262);
((GeneralPath)shape).curveTo(162.11339, 25.459301, 162.19669, 23.36791, 162.42036, 21.467747);
((GeneralPath)shape).curveTo(162.43367, 21.325554, 162.44637, 21.24266, 162.44637, 21.24266);
((GeneralPath)shape).curveTo(162.44336, 21.31854, 162.42346, 21.39187, 162.42036, 21.467747);
((GeneralPath)shape).curveTo(162.34987, 22.09327, 162.20142, 24.05589, 162.40436, 27.599636);
((GeneralPath)shape).curveTo(163.84935, 26.421396, 165.92906, 24.424215, 167.57405, 22.71668);
((GeneralPath)shape).curveTo(165.83643, 19.166529, 163.67468, 18.54636, 163.67468, 18.54636);
((GeneralPath)shape).curveTo(163.67468, 18.54636, 160.58243, 17.299791, 159.65111, 19.498638);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(171.75146, 46.308453);
((GeneralPath)shape).curveTo(171.5883, 46.48705, 171.41814, 46.656082, 171.24796, 46.825115);
((GeneralPath)shape).curveTo(171.24796, 46.825115, 171.24796, 46.825115, 171.24796, 46.825115);
((GeneralPath)shape).curveTo(171.33655, 46.745377, 171.42769, 46.649063, 171.50928, 46.559765);
((GeneralPath)shape).curveTo(171.59787, 46.480026, 171.67944, 46.39073, 171.75146, 46.308453);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_5;
g.setTransform(defaultTransform__0_0_5);
g.setClip(clip__0_0_5);
float alpha__0_0_6 = origAlpha;
origAlpha = origAlpha * 0.35f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_6 = g.getClip();
AffineTransform defaultTransform__0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_6 is ShapeNode
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(171.75146, 46.308453);
((GeneralPath)shape).curveTo(171.5883, 46.48705, 171.41814, 46.656082, 171.24796, 46.825115);
((GeneralPath)shape).curveTo(171.24796, 46.825115, 171.24796, 46.825115, 171.24796, 46.825115);
((GeneralPath)shape).curveTo(171.33655, 46.745377, 171.42769, 46.649063, 171.50928, 46.559765);
((GeneralPath)shape).curveTo(171.59787, 46.480026, 171.67944, 46.39073, 171.75146, 46.308453);
((GeneralPath)shape).closePath();
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7 is ShapeNode
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(170.47324, 43.524525);
((GeneralPath)shape).curveTo(170.47324, 43.524525, 170.47324, 43.524525, 170.47324, 43.524525);
((GeneralPath)shape).curveTo(170.46324, 43.531525, 170.46324, 43.531525, 170.47324, 43.524525);
((GeneralPath)shape).curveTo(170.50194, 43.503475, 170.52364, 43.472855, 170.55223, 43.451805);
((GeneralPath)shape).curveTo(170.6625, 43.341454, 170.77277, 43.231106, 170.86644, 43.11821);
((GeneralPath)shape).curveTo(170.74406, 43.252155, 170.60513, 43.38356, 170.4732, 43.524525);
((GeneralPath)shape).lineTo(170.4732, 43.524525);
((GeneralPath)shape).lineTo(170.4732, 43.524525);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_7;
g.setTransform(defaultTransform__0_0_7);
g.setClip(clip__0_0_7);
float alpha__0_0_8 = origAlpha;
origAlpha = origAlpha * 0.35f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_8 = g.getClip();
AffineTransform defaultTransform__0_0_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_8 is ShapeNode
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(170.47324, 43.524525);
((GeneralPath)shape).curveTo(170.47324, 43.524525, 170.47324, 43.524525, 170.47324, 43.524525);
((GeneralPath)shape).curveTo(170.46324, 43.531525, 170.46324, 43.531525, 170.47324, 43.524525);
((GeneralPath)shape).curveTo(170.50194, 43.503475, 170.52364, 43.472855, 170.55223, 43.451805);
((GeneralPath)shape).curveTo(170.6625, 43.341454, 170.77277, 43.231106, 170.86644, 43.11821);
((GeneralPath)shape).curveTo(170.74406, 43.252155, 170.60513, 43.38356, 170.4732, 43.524525);
((GeneralPath)shape).lineTo(170.4732, 43.524525);
((GeneralPath)shape).lineTo(170.4732, 43.524525);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = new LinearGradientPaint(new Point2D.Double(-8268.638671875, -813.1232299804688), new Point2D.Double(-7728.95849609375, -813.1232299804688), new float[] {0.3233f,0.6302f,0.7514f,1.0f}, new Color[] {getColor(158, 32, 100, 255, disabled),getColor(201, 32, 55, 255, disabled),getColor(205, 35, 53, 255, disabled),getColor(233, 120, 38, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.023193469271063805f, -0.11631035059690475f, 0.11631035059690475f, -0.023193469271063805f, 74.81830596923828f, -889.8349609375f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(163.08434, 37.98729);
((GeneralPath)shape).curveTo(162.92319, 36.82563, 162.7761, 35.683094, 162.64302, 34.55968);
((GeneralPath)shape).curveTo(162.50803, 33.393547, 162.40363, 32.24908, 162.32285, 31.11672);
((GeneralPath)shape).curveTo(162.31685, 31.047861, 162.31004, 30.979002, 162.31064, 30.919704);
((GeneralPath)shape).curveTo(162.23434, 29.813484, 162.17906, 28.735947, 162.1544, 27.680077);
((GeneralPath)shape).lineTo(158.67047, 26.000092);
((GeneralPath)shape).curveTo(158.66347, 26.211143, 158.64706, 26.42921, 158.63757, 26.656841);
((GeneralPath)shape).curveTo(158.60287, 27.491486, 158.57707, 28.378408, 158.56978, 29.310595);
((GeneralPath)shape).curveTo(158.56078, 30.361378, 158.57079, 31.457422, 158.59018, 32.605747);
((GeneralPath)shape).curveTo(158.61488, 33.661617, 158.65797, 34.762753, 158.71518, 35.883007);
((GeneralPath)shape).curveTo(158.77168, 36.84195, 158.83517, 37.81045, 158.91272, 38.798077);
((GeneralPath)shape).curveTo(158.91472, 38.840797, 158.91872, 38.866936, 158.92072, 38.909657);
((GeneralPath)shape).lineTo(163.5813, 41.270775);
((GeneralPath)shape).curveTo(163.41956, 40.168415, 163.24823, 39.07307, 163.08394, 37.98729);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = new LinearGradientPaint(new Point2D.Double(-8203.4921875, -758.9940185546875), new Point2D.Double(-7881.89501953125, -758.9940185546875), new float[] {0.0f,0.0954839f,0.7882f,0.9487f}, new Color[] {getColor(40, 38, 98, 255, disabled),getColor(102, 46, 141, 255, disabled),getColor(159, 32, 100, 255, disabled),getColor(205, 32, 50, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.023193469271063805f, -0.11631035059690475f, 0.11631035059690475f, -0.023193469271063805f, 74.81830596923828f, -889.8349609375f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(169.97002, 65.549644);
((GeneralPath)shape).curveTo(170.22667, 66.42052, 170.48782, 67.31753, 170.77255, 68.22665);
((GeneralPath)shape).curveTo(170.77956, 68.236244, 170.77655, 68.252785, 170.78406, 68.262344);
((GeneralPath)shape).curveTo(170.82295, 68.39559, 170.85497, 68.51927, 170.90344, 68.6455);
((GeneralPath)shape).curveTo(171.08923, 69.259445, 171.26862, 69.80453, 171.65422, 71.05154);
((GeneralPath)shape).curveTo(172.4078, 70.89566, 173.34842, 71.23513, 174.33052, 71.69127);
((GeneralPath)shape).curveTo(173.70026, 70.93276, 172.8278, 70.425575, 171.78528, 70.30804);
((GeneralPath)shape).curveTo(174.63974, 68.404274, 176.51779, 65.95192, 176.25392, 63.026676);
((GeneralPath)shape).curveTo(176.22572, 62.76782, 176.19063, 62.499397, 176.13882, 62.228436);
((GeneralPath)shape).curveTo(176.09402, 63.350006, 175.63016, 64.16123, 174.2641, 65.046135);
((GeneralPath)shape).curveTo(174.2641, 65.046135, 174.2541, 65.05314, 174.2541, 65.05314);
((GeneralPath)shape).curveTo(174.2541, 65.05314, 174.2641, 65.046135, 174.2641, 65.046135);
((GeneralPath)shape).curveTo(175.55435, 62.775097, 175.90953, 61.234493, 175.75697, 59.242664);
((GeneralPath)shape).curveTo(175.71918, 58.77021, 175.66028, 58.269073, 175.57338, 57.72969);
((GeneralPath)shape).curveTo(175.16075, 60.695374, 173.56813, 62.61457, 171.23875, 63.750443);
((GeneralPath)shape).lineTo(169.8257, 65.05237);
((GeneralPath)shape).curveTo(169.8761, 65.22131, 169.9196, 65.3807, 169.97, 65.549644);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = new LinearGradientPaint(new Point2D.Double(-8238.328125, -818.1522216796875), new Point2D.Double(-7698.64697265625, -818.1522216796875), new float[] {0.3233f,0.6302f,0.7514f,1.0f}, new Color[] {getColor(158, 32, 100, 255, disabled),getColor(201, 32, 55, 255, disabled),getColor(205, 35, 53, 255, disabled),getColor(233, 120, 38, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.023193469271063805f, -0.11631035059690475f, 0.11631035059690475f, -0.023193469271063805f, 74.81830596923828f, -889.8349609375f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(168.1538, 61.57169);
((GeneralPath)shape).curveTo(167.85686, 60.465557, 167.56247, 59.342846, 167.26108, 58.21057);
((GeneralPath)shape).curveTo(166.9782, 57.12356, 166.70486, 56.029533, 166.43407, 54.918926);
((GeneralPath)shape).curveTo(166.16328, 53.80832, 165.90205, 52.690697, 165.64082, 51.57307);
((GeneralPath)shape).curveTo(165.37512, 50.429306, 165.126, 49.288086, 164.88644, 48.139847);
((GeneralPath)shape).curveTo(164.64433, 47.008186, 164.42136, 45.862495, 164.19583, 44.73338);
((GeneralPath)shape).curveTo(164.11473, 44.322155, 164.04056, 43.92049, 163.96898, 43.502247);
((GeneralPath)shape).curveTo(163.83862, 42.80348, 163.71783, 42.097694, 163.59705, 41.39191);
((GeneralPath)shape).curveTo(163.58554, 41.35621, 163.58365, 41.31349, 163.57214, 41.27779);
((GeneralPath)shape).lineTo(158.9377, 38.912197);
((GeneralPath)shape).curveTo(158.9437, 38.981056, 158.9575, 39.059475, 158.95439, 39.135353);
((GeneralPath)shape).curveTo(159.0479, 40.18482, 159.13887, 41.250866, 159.26299, 42.322);
((GeneralPath)shape).curveTo(159.38457, 43.40971, 159.52272, 44.499966, 159.68448, 45.60233);
((GeneralPath)shape).curveTo(159.82195, 46.53127, 159.96896, 47.453194, 160.12299, 48.384678);
((GeneralPath)shape).curveTo(160.15428, 48.56766, 160.19258, 48.7602, 160.22395, 48.943184);
((GeneralPath)shape).curveTo(160.43738, 50.095898, 160.66544, 51.20843, 160.91324, 52.247635);
((GeneralPath)shape).curveTo(161.19044, 53.4271, 161.48737, 54.533234, 161.78745, 55.563488);
((GeneralPath)shape).curveTo(161.9892, 56.239273, 162.18648, 56.88892, 162.38884, 57.505405);
((GeneralPath)shape).curveTo(162.56375, 58.02435, 162.75523, 58.545837, 162.93971, 59.057762);
((GeneralPath)shape).curveTo(163.38269, 60.262665, 163.87349, 61.432484, 164.40251, 62.57423);
((GeneralPath)shape).lineTo(169.08669, 64.94746);
((GeneralPath)shape).curveTo(168.83704, 64.08614, 168.60652, 63.2108, 168.35239, 62.323353);
((GeneralPath)shape).curveTo(168.28848, 62.075985, 168.22467, 61.828617, 168.1538, 61.57169);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = new LinearGradientPaint(new Point2D.Double(-8198.9677734375, -810.8505859375), new Point2D.Double(-7915.35009765625, -810.8505859375), new float[] {0.0f,0.0954839f,0.7882f,0.9487f}, new Color[] {getColor(40, 38, 98, 255, disabled),getColor(102, 46, 141, 255, disabled),getColor(159, 32, 100, 255, disabled),getColor(205, 32, 50, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.023193469271063805f, -0.11631035059690475f, 0.11631035059690475f, -0.023193469271063805f, 74.81830596923828f, -889.8349609375f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(164.47206, 62.72914);
((GeneralPath)shape).curveTo(165.11723, 64.10931, 165.82933, 65.44035, 166.62749, 66.70824);
((GeneralPath)shape).curveTo(166.6556, 66.74649, 166.68109, 66.80131, 166.70918, 66.839554);
((GeneralPath)shape).curveTo(165.41463, 66.318504, 163.1206, 66.56043, 163.13719, 66.56298);
((GeneralPath)shape).curveTo(165.5056, 67.38455, 167.58269, 68.39051, 168.89294, 69.915146);
((GeneralPath)shape).curveTo(168.37225, 70.48856, 167.39752, 70.92444, 166.17218, 71.279434);
((GeneralPath)shape).curveTo(167.86638, 71.46303, 168.8112, 71.1668, 169.15672, 71.01619);
((GeneralPath)shape).curveTo(168.27138, 71.75426, 167.77585, 73.10363, 167.34094, 74.55564);
((GeneralPath)shape).curveTo(168.33751, 72.92679, 169.32602, 71.8482, 170.28864, 71.43592);
((GeneralPath)shape).curveTo(171.6945, 76.09756, 173.38809, 81.15121, 175.26414, 86.453445);
((GeneralPath)shape).curveTo(175.58151, 86.043976, 175.61703, 85.591255, 175.48422, 85.129684);
((GeneralPath)shape).curveTo(175.13826, 84.17724, 172.9073, 77.87026, 170.36047, 69.30883);
((GeneralPath)shape).curveTo(170.28708, 69.06848, 170.22319, 68.82111, 170.14276, 68.571205);
((GeneralPath)shape).curveTo(170.11977, 68.4998, 170.10387, 68.43796, 170.08086, 68.366554);
((GeneralPath)shape).curveTo(169.81972, 67.46954, 169.55411, 66.546394, 169.28401, 65.5971);
((GeneralPath)shape).curveTo(169.22461, 65.37587, 169.16269, 65.17123, 169.10332, 64.95);
((GeneralPath)shape).curveTo(169.10332, 64.95, 169.09631, 64.9404, 169.09631, 64.9404);
((GeneralPath)shape).lineTo(164.41214, 62.567173);
((GeneralPath)shape).curveTo(164.42815, 62.629013, 164.44664, 62.67428, 164.47214, 62.7291);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = new LinearGradientPaint(new Point2D.Double(-8238.328125, -762.297119140625), new Point2D.Double(-7698.64697265625, -762.297119140625), new float[] {0.3233f,0.6302f,0.7514f,1.0f}, new Color[] {getColor(158, 32, 100, 255, disabled),getColor(201, 32, 55, 255, disabled),getColor(205, 35, 53, 255, disabled),getColor(233, 120, 38, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.023193469271063805f, -0.11631035059690475f, 0.11631035059690475f, -0.023193469271063805f, 74.81830596923828f, -889.8349609375f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(164.12625, 41.3713);
((GeneralPath)shape).curveTo(164.15305, 41.52814, 164.18245, 41.668404, 164.20935, 41.825245);
((GeneralPath)shape).curveTo(164.29945, 42.28875, 164.37997, 42.759274, 164.47707, 43.232338);
((GeneralPath)shape).curveTo(164.57607, 43.748123, 164.68216, 44.27347, 164.78566, 44.8154);
((GeneralPath)shape).curveTo(164.83736, 45.08636, 164.88918, 45.357327, 164.94794, 45.63785);
((GeneralPath)shape).curveTo(165.11723, 46.469864, 165.30057, 47.321, 165.48837, 48.198273);
((GeneralPath)shape).curveTo(165.72153, 49.27765, 165.97574, 50.385715, 166.24846, 51.53904);
((GeneralPath)shape).curveTo(166.5052, 52.630524, 166.78047, 53.76727, 167.06976, 54.923138);
((GeneralPath)shape).curveTo(167.35011, 56.026726, 167.63495, 57.156456, 167.94783, 58.324432);
((GeneralPath)shape).curveTo(168.22179, 59.35916, 168.51678, 60.422577, 168.81625, 61.51213);
((GeneralPath)shape).curveTo(168.82526, 61.56441, 168.85075, 61.619236, 168.85965, 61.671516);
((GeneralPath)shape).curveTo(169.16167, 62.74449, 169.47517, 63.85317, 169.80272, 64.98097);
((GeneralPath)shape).curveTo(169.80672, 65.00711, 169.81172, 65.03325, 169.82571, 65.052376);
((GeneralPath)shape).lineTo(171.23877, 63.750454);
((GeneralPath)shape).curveTo(171.20306, 63.761944, 171.17436, 63.782993, 171.12912, 63.801502);
((GeneralPath)shape).curveTo(172.80605, 62.21765, 174.24323, 59.04434, 174.57172, 56.184532);
((GeneralPath)shape).curveTo(174.72295, 54.867172, 174.709, 53.465076, 174.52792, 51.935524);
((GeneralPath)shape).curveTo(174.3974, 50.795532, 174.16547, 49.597557, 173.84679, 48.301426);
((GeneralPath)shape).curveTo(173.56897, 47.18126, 173.22546, 45.991615, 172.8067, 44.73952);
((GeneralPath)shape).curveTo(172.54741, 45.268204, 172.17908, 45.788635, 171.72784, 46.296352);
((GeneralPath)shape).curveTo(171.64624, 46.38565, 171.5647, 46.47495, 171.48311, 46.564247);
((GeneralPath)shape).curveTo(171.4015, 46.653545, 171.31293, 46.73328, 171.22179, 46.829597);
((GeneralPath)shape).lineTo(171.22179, 46.829597);
((GeneralPath)shape).lineTo(171.22179, 46.829597);
((GeneralPath)shape).curveTo(171.22179, 46.829597, 171.22179, 46.829597, 171.22179, 46.829597);
((GeneralPath)shape).curveTo(172.45863, 44.906715, 172.60573, 42.84208, 172.23889, 40.698586);
((GeneralPath)shape).curveTo(172.01407, 41.33437, 171.57259, 42.276295, 170.8403, 43.122692);
((GeneralPath)shape).curveTo(170.7466, 43.235584, 170.63637, 43.345936, 170.5261, 43.456287);
((GeneralPath)shape).curveTo(170.49739, 43.477337, 170.4758, 43.507957, 170.4375, 43.536026);
((GeneralPath)shape).lineTo(170.4375, 43.536026);
((GeneralPath)shape).lineTo(170.4375, 43.536026);
((GeneralPath)shape).curveTo(170.4375, 43.536026, 170.4375, 43.536026, 170.4375, 43.536026);
((GeneralPath)shape).curveTo(170.4375, 43.536026, 170.4375, 43.536026, 170.4375, 43.536026);
((GeneralPath)shape).lineTo(170.4375, 43.536026);
((GeneralPath)shape).curveTo(170.84785, 42.852345, 171.1511, 42.20314, 171.3453, 41.54569);
((GeneralPath)shape).curveTo(171.3918, 41.408585, 171.4217, 41.268936, 171.45161, 41.129288);
((GeneralPath)shape).curveTo(171.50131, 40.916306, 171.53432, 40.700783, 171.5674, 40.485256);
((GeneralPath)shape).curveTo(171.5807, 40.343063, 171.6011, 40.210434, 171.6144, 40.06824);
((GeneralPath)shape).curveTo(171.6391, 39.74114, 171.64029, 39.401928, 171.6084, 39.057632);
((GeneralPath)shape).curveTo(171.6004, 38.946053, 171.5984, 38.844036, 171.5809, 38.739475);
((GeneralPath)shape).curveTo(171.55411, 38.582634, 171.5342, 38.435356, 171.5048, 38.295094);
((GeneralPath)shape).curveTo(171.39296, 37.64159, 171.2684, 37.07098, 171.14069, 36.576244);
((GeneralPath)shape).curveTo(171.07678, 36.328876, 171.01744, 36.10765, 170.9555, 35.903004);
((GeneralPath)shape).curveTo(170.9255, 35.822044, 170.91211, 35.743618, 170.88211, 35.662655);
((GeneralPath)shape).curveTo(170.8061, 35.438885, 170.74422, 35.234238, 170.6772, 35.062748);
((GeneralPath)shape).curveTo(170.5802, 34.810295, 170.49211, 34.61012, 170.42001, 34.471786);
((GeneralPath)shape).lineTo(170.42001, 34.471786);
((GeneralPath)shape).lineTo(170.42001, 34.471786);
((GeneralPath)shape).curveTo(170.39772, 34.561695, 170.36592, 34.658627, 170.31746, 34.753014);
((GeneralPath)shape).curveTo(170.02571, 35.437923, 169.34703, 36.377388, 168.72818, 37.037563);
((GeneralPath)shape).lineTo(170.03098, 35.84599);
((GeneralPath)shape).lineTo(168.72818, 37.037563);
((GeneralPath)shape).curveTo(168.71819, 37.044563, 168.70908, 37.051594, 168.70648, 37.068172);
((GeneralPath)shape).curveTo(168.64658, 37.126854, 168.5771, 37.19256, 168.51465, 37.267822);
((GeneralPath)shape).curveTo(168.51765, 37.251244, 168.53635, 37.237213, 168.53885, 37.22063);
((GeneralPath)shape).lineTo(164.08678, 41.297363);
((GeneralPath)shape).curveTo(164.12437, 41.328594, 164.12888, 41.354733, 164.12637, 41.371315);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = new LinearGradientPaint(new Point2D.Double(-8271.8056640625, -765.0706787109375), new Point2D.Double(-7732.125, -765.0706787109375), new float[] {0.3233f,0.6302f,0.7514f,1.0f}, new Color[] {getColor(158, 32, 100, 255, disabled),getColor(201, 32, 55, 255, disabled),getColor(205, 35, 53, 255, disabled),getColor(233, 120, 38, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.023193469271063805f, -0.11631035059690475f, 0.11631035059690475f, -0.023193469271063805f, 74.81830596923828f, -889.8349609375f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(162.40433, 27.599638);
((GeneralPath)shape).curveTo(162.45822, 28.575157, 162.54416, 29.67436, 162.65495, 30.887686);
((GeneralPath)shape).curveTo(162.66095, 30.956545, 162.66095, 31.015842, 162.67665, 31.077684);
((GeneralPath)shape).curveTo(162.77718, 32.13671, 162.9077, 33.276707, 163.07271, 34.523804);
((GeneralPath)shape).curveTo(163.21342, 35.59748, 163.37007, 36.732998, 163.56377, 37.959045);
((GeneralPath)shape).curveTo(163.72359, 39.01869, 163.9185, 40.126137, 164.11981, 41.302444);
((GeneralPath)shape).lineTo(168.57188, 37.22571);
((GeneralPath)shape).curveTo(169.50021, 35.54397, 169.7032, 34.49757, 169.69055, 33.19749);
((GeneralPath)shape).curveTo(169.68456, 32.84872, 169.66475, 32.480827, 169.63785, 32.103374);
((GeneralPath)shape).curveTo(169.56665, 30.963997, 169.42969, 29.755144, 169.2354, 28.588398);
((GeneralPath)shape).curveTo(169.05258, 27.457354, 168.829, 26.370956, 168.57294, 25.440788);
((GeneralPath)shape).curveTo(168.41075, 24.838951, 168.23837, 24.303427, 168.06285, 23.84378);
((GeneralPath)shape).curveTo(167.90327, 23.445976, 167.73865, 23.08133, 167.57144, 22.733263);
((GeneralPath)shape).curveTo(165.929, 24.424217, 163.84929, 26.4214, 162.4043, 27.59964);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(171.5163, 46.56933);
((GeneralPath)shape).curveTo(171.4347, 46.658627, 171.34612, 46.738365, 171.25497, 46.83468);
((GeneralPath)shape).lineTo(171.25497, 46.83468);
((GeneralPath)shape).curveTo(171.33658, 46.74538, 171.41814, 46.656082, 171.5163, 46.56933);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_15;
g.setTransform(defaultTransform__0_0_15);
g.setClip(clip__0_0_15);
float alpha__0_0_16 = origAlpha;
origAlpha = origAlpha * 0.35f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_16 = g.getClip();
AffineTransform defaultTransform__0_0_16 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_16 is ShapeNode
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(171.5163, 46.56933);
((GeneralPath)shape).curveTo(171.4347, 46.658627, 171.34612, 46.738365, 171.25497, 46.83468);
((GeneralPath)shape).lineTo(171.25497, 46.83468);
((GeneralPath)shape).curveTo(171.33658, 46.74538, 171.41814, 46.656082, 171.5163, 46.56933);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = new LinearGradientPaint(new Point2D.Double(-8238.328125, -745.684814453125), new Point2D.Double(-7698.64697265625, -745.684814453125), new float[] {0.3233f,0.6302f,0.7514f,1.0f}, new Color[] {getColor(158, 32, 100, 255, disabled),getColor(201, 32, 55, 255, disabled),getColor(205, 35, 53, 255, disabled),getColor(233, 120, 38, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.023193469271063805f, -0.11631035059690475f, 0.11631035059690475f, -0.023193469271063805f, 74.81830596923828f, -889.8349609375f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(171.5163, 46.56933);
((GeneralPath)shape).curveTo(171.4347, 46.658627, 171.34612, 46.738365, 171.25497, 46.83468);
((GeneralPath)shape).lineTo(171.25497, 46.83468);
((GeneralPath)shape).curveTo(171.33658, 46.74538, 171.41814, 46.656082, 171.5163, 46.56933);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(170.46367, 43.531544);
((GeneralPath)shape).curveTo(170.49237, 43.510494, 170.51396, 43.479874, 170.55226, 43.451805);
((GeneralPath)shape).curveTo(170.52356, 43.472855, 170.50197, 43.503475, 170.46367, 43.531544);
((GeneralPath)shape).lineTo(170.46367, 43.531544);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_18;
g.setTransform(defaultTransform__0_0_18);
g.setClip(clip__0_0_18);
float alpha__0_0_19 = origAlpha;
origAlpha = origAlpha * 0.35f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_19 = g.getClip();
AffineTransform defaultTransform__0_0_19 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_19 is ShapeNode
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(170.46367, 43.531544);
((GeneralPath)shape).curveTo(170.49237, 43.510494, 170.51396, 43.479874, 170.55226, 43.451805);
((GeneralPath)shape).curveTo(170.52356, 43.472855, 170.50197, 43.503475, 170.46367, 43.531544);
((GeneralPath)shape).lineTo(170.46367, 43.531544);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = new LinearGradientPaint(new Point2D.Double(-8238.328125, -747.5855712890625), new Point2D.Double(-7698.64697265625, -747.5855712890625), new float[] {0.3233f,0.6302f,0.7514f,1.0f}, new Color[] {getColor(158, 32, 100, 255, disabled),getColor(201, 32, 55, 255, disabled),getColor(205, 35, 53, 255, disabled),getColor(233, 120, 38, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.023193469271063805f, -0.11631035059690475f, 0.11631035059690475f, -0.023193469271063805f, 74.81830596923828f, -889.8349609375f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(170.46367, 43.531544);
((GeneralPath)shape).curveTo(170.49237, 43.510494, 170.51396, 43.479874, 170.55226, 43.451805);
((GeneralPath)shape).curveTo(170.52356, 43.472855, 170.50197, 43.503475, 170.46367, 43.531544);
((GeneralPath)shape).lineTo(170.46367, 43.531544);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(170.47324, 43.524525);
((GeneralPath)shape).curveTo(170.47324, 43.524525, 170.47324, 43.524525, 170.47324, 43.524525);
((GeneralPath)shape).lineTo(170.47324, 43.524525);
((GeneralPath)shape).lineTo(170.47324, 43.524525);
((GeneralPath)shape).lineTo(170.47324, 43.524525);
((GeneralPath)shape).curveTo(170.47324, 43.524525, 170.47324, 43.524525, 170.47324, 43.524525);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_21;
g.setTransform(defaultTransform__0_0_21);
g.setClip(clip__0_0_21);
float alpha__0_0_22 = origAlpha;
origAlpha = origAlpha * 0.35f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_22 = g.getClip();
AffineTransform defaultTransform__0_0_22 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_22 is ShapeNode
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(170.47324, 43.524525);
((GeneralPath)shape).curveTo(170.47324, 43.524525, 170.47324, 43.524525, 170.47324, 43.524525);
((GeneralPath)shape).lineTo(170.47324, 43.524525);
((GeneralPath)shape).lineTo(170.47324, 43.524525);
((GeneralPath)shape).lineTo(170.47324, 43.524525);
((GeneralPath)shape).curveTo(170.47324, 43.524525, 170.47324, 43.524525, 170.47324, 43.524525);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = new LinearGradientPaint(new Point2D.Double(-7935.14306640625, -747.966796875), new Point2D.Double(-7815.85595703125, -747.966796875), new float[] {0.3233f,0.6302f,0.7514f,1.0f}, new Color[] {getColor(158, 32, 100, 255, disabled),getColor(201, 32, 55, 255, disabled),getColor(205, 35, 53, 255, disabled),getColor(233, 120, 38, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.023193469271063805f, -0.11631035059690475f, 0.11631035059690475f, -0.023193469271063805f, 74.81830596923828f, -889.8349609375f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(170.47324, 43.524525);
((GeneralPath)shape).curveTo(170.47324, 43.524525, 170.47324, 43.524525, 170.47324, 43.524525);
((GeneralPath)shape).lineTo(170.47324, 43.524525);
((GeneralPath)shape).lineTo(170.47324, 43.524525);
((GeneralPath)shape).lineTo(170.47324, 43.524525);
((GeneralPath)shape).curveTo(170.47324, 43.524525, 170.47324, 43.524525, 170.47324, 43.524525);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_23;
g.setTransform(defaultTransform__0_0_23);
g.setClip(clip__0_0_23);
float alpha__0_0_24 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_24 = g.getClip();
AffineTransform defaultTransform__0_0_24 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_24 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(-7708.796875, -803.360107421875), new Point2D.Double(-7633.15283203125, -714.9074096679688), new float[] {0.0f,0.3123f,0.8383f}, new Color[] {getColor(246, 153, 35, 255, disabled),getColor(247, 154, 35, 255, disabled),getColor(233, 120, 38, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.07373976707458496f, -0.11205927282571793f, 0.11205927282571793f, 0.07373976707458496f, 859.45849609375f, -779.0554809570312f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(205.26672, 20.373257);
((GeneralPath)shape).curveTo(204.05446, 20.873299, 201.92366, 22.458813, 199.32507, 24.829863);
((GeneralPath)shape).lineTo(200.79295, 28.963598);
((GeneralPath)shape).curveTo(202.51303, 27.128002, 204.24176, 25.510609, 205.93155, 24.158669);
((GeneralPath)shape).curveTo(206.05525, 24.054794, 206.13136, 23.998169, 206.13136, 23.998169);
((GeneralPath)shape).curveTo(206.06856, 24.05674, 205.99438, 24.100096, 205.93155, 24.158669);
((GeneralPath)shape).curveTo(205.37779, 24.60619, 203.69897, 26.068207, 201.04672, 29.082184);
((GeneralPath)shape).curveTo(203.14609, 29.281755, 206.4071, 29.326372, 209.08801, 29.258757);
((GeneralPath)shape).curveTo(210.50986, 25.020256, 209.26236, 22.803465, 209.26236, 22.803465);
((GeneralPath)shape).curveTo(209.26236, 22.803465, 207.76347, 19.343096, 205.26672, 20.373259);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_24;
g.setTransform(defaultTransform__0_0_24);
g.setClip(clip__0_0_24);
float alpha__0_0_25 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_25 = g.getClip();
AffineTransform defaultTransform__0_0_25 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_25 is ShapeNode
origAlpha = alpha__0_0_25;
g.setTransform(defaultTransform__0_0_25);
g.setClip(clip__0_0_25);
float alpha__0_0_26 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_26 = g.getClip();
AffineTransform defaultTransform__0_0_26 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_26 is ShapeNode
origAlpha = alpha__0_0_26;
g.setTransform(defaultTransform__0_0_26);
g.setClip(clip__0_0_26);
float alpha__0_0_27 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_27 = g.getClip();
AffineTransform defaultTransform__0_0_27 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_27 is ShapeNode
origAlpha = alpha__0_0_27;
g.setTransform(defaultTransform__0_0_27);
g.setClip(clip__0_0_27);
float alpha__0_0_28 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_28 = g.getClip();
AffineTransform defaultTransform__0_0_28 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_28 is ShapeNode
origAlpha = alpha__0_0_28;
g.setTransform(defaultTransform__0_0_28);
g.setClip(clip__0_0_28);
float alpha__0_0_29 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_29 = g.getClip();
AffineTransform defaultTransform__0_0_29 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_29 is ShapeNode
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(193.7068, 51.569557);
((GeneralPath)shape).curveTo(193.43356, 51.5837, 193.16228, 51.584568, 192.89098, 51.585438);
((GeneralPath)shape).curveTo(192.89098, 51.585438, 192.89098, 51.585438, 192.89098, 51.585438);
((GeneralPath)shape).curveTo(193.02565, 51.591637, 193.17555, 51.586536, 193.31216, 51.579437);
((GeneralPath)shape).curveTo(193.44684, 51.585636, 193.58347, 51.578568, 193.7068, 51.56954);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_29;
g.setTransform(defaultTransform__0_0_29);
g.setClip(clip__0_0_29);
float alpha__0_0_30 = origAlpha;
origAlpha = origAlpha * 0.35f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_30 = g.getClip();
AffineTransform defaultTransform__0_0_30 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_30 is ShapeNode
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(193.7068, 51.569557);
((GeneralPath)shape).curveTo(193.43356, 51.5837, 193.16228, 51.584568, 192.89098, 51.585438);
((GeneralPath)shape).curveTo(192.89098, 51.585438, 192.89098, 51.585438, 192.89098, 51.585438);
((GeneralPath)shape).curveTo(193.02565, 51.591637, 193.17555, 51.586536, 193.31216, 51.579437);
((GeneralPath)shape).curveTo(193.44684, 51.585636, 193.58347, 51.578568, 193.7068, 51.56954);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_30;
g.setTransform(defaultTransform__0_0_30);
g.setClip(clip__0_0_30);
float alpha__0_0_31 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_31 = g.getClip();
AffineTransform defaultTransform__0_0_31 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_31 is ShapeNode
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(194.8897, 48.312866);
((GeneralPath)shape).curveTo(194.8897, 48.312866, 194.8897, 48.312866, 194.8897, 48.312866);
((GeneralPath)shape).curveTo(194.87639, 48.310966, 194.87639, 48.310966, 194.8897, 48.312866);
((GeneralPath)shape).curveTo(194.92949, 48.318665, 194.9713, 48.311268, 195.0111, 48.317165);
((GeneralPath)shape).curveTo(195.18753, 48.315964, 195.36397, 48.314667, 195.52908, 48.298264);
((GeneralPath)shape).curveTo(195.32416, 48.308872, 195.1079, 48.304264, 194.8897, 48.312912);
((GeneralPath)shape).lineTo(194.8897, 48.312912);
((GeneralPath)shape).lineTo(194.8897, 48.312912);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_31;
g.setTransform(defaultTransform__0_0_31);
g.setClip(clip__0_0_31);
float alpha__0_0_32 = origAlpha;
origAlpha = origAlpha * 0.35f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_32 = g.getClip();
AffineTransform defaultTransform__0_0_32 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_32 is ShapeNode
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(194.8897, 48.312866);
((GeneralPath)shape).curveTo(194.8897, 48.312866, 194.8897, 48.312866, 194.8897, 48.312866);
((GeneralPath)shape).curveTo(194.87639, 48.310966, 194.87639, 48.310966, 194.8897, 48.312866);
((GeneralPath)shape).curveTo(194.92949, 48.318665, 194.9713, 48.311268, 195.0111, 48.317165);
((GeneralPath)shape).curveTo(195.18753, 48.315964, 195.36397, 48.314667, 195.52908, 48.298264);
((GeneralPath)shape).curveTo(195.32416, 48.308872, 195.1079, 48.304264, 194.8897, 48.312912);
((GeneralPath)shape).lineTo(194.8897, 48.312912);
((GeneralPath)shape).lineTo(194.8897, 48.312912);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_32;
g.setTransform(defaultTransform__0_0_32);
g.setClip(clip__0_0_32);
float alpha__0_0_33 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_33 = g.getClip();
AffineTransform defaultTransform__0_0_33 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_33 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(-8268.638671875, -813.1232299804688), new Point2D.Double(-7728.95849609375, -813.1232299804688), new float[] {0.3233f,0.6302f,0.7514f,1.0f}, new Color[] {getColor(158, 32, 100, 255, disabled),getColor(201, 32, 55, 255, disabled),getColor(205, 35, 53, 255, disabled),getColor(233, 120, 38, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.07373976707458496f, -0.11205927282571793f, 0.11205927282571793f, 0.07373976707458496f, 859.45849609375f, -779.0554809570312f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(193.34094, 37.984676);
((GeneralPath)shape).curveTo(194.13419, 36.921494, 194.92354, 35.884857, 195.70898, 34.874763);
((GeneralPath)shape).curveTo(196.52681, 33.828747, 197.35208, 32.824497, 198.18674, 31.848742);
((GeneralPath)shape).curveTo(198.23634, 31.788221, 198.28584, 31.727701, 198.33345, 31.680454);
((GeneralPath)shape).curveTo(199.15092, 30.729294, 199.96255, 29.817953, 200.78163, 28.948376);
((GeneralPath)shape).lineTo(199.3118, 24.827913);
((GeneralPath)shape).curveTo(199.13853, 24.992308, 198.95201, 25.154753, 198.76352, 25.330473);
((GeneralPath)shape).curveTo(198.07242, 25.974775, 197.34698, 26.668274, 196.60048, 27.412914);
((GeneralPath)shape).curveTo(195.75876, 28.25205, 194.89598, 29.14233, 193.99887, 30.081806);
((GeneralPath)shape).curveTo(193.1798, 30.951382, 192.33968, 31.8721, 191.49565, 32.819366);
((GeneralPath)shape).curveTo(190.77924, 33.63621, 190.06087, 34.46633, 189.33861, 35.322994);
((GeneralPath)shape).curveTo(189.30621, 35.358913, 189.289, 35.383514, 189.25671, 35.419434);
((GeneralPath)shape).lineTo(191.1326, 41.023098);
((GeneralPath)shape).curveTo(191.87823, 40.007164, 192.6106, 38.98928, 193.341, 37.984673);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_33;
g.setTransform(defaultTransform__0_0_33);
g.setClip(clip__0_0_33);
float alpha__0_0_34 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_34 = g.getClip();
AffineTransform defaultTransform__0_0_34 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_34 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(-8203.4921875, -758.9940185546875), new Point2D.Double(-7881.89501953125, -758.9940185546875), new float[] {0.0f,0.0954839f,0.7882f,0.9487f}, new Color[] {getColor(40, 38, 98, 255, disabled),getColor(102, 46, 141, 255, disabled),getColor(159, 32, 100, 255, disabled),getColor(205, 32, 50, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.07373976707458496f, -0.11205927282571793f, 0.11205927282571793f, 0.07373976707458496f, 859.45849609375f, -779.0554809570312f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(176.98518, 65.6436);
((GeneralPath)shape).curveTo(176.49988, 66.54859, 175.99742, 67.47818, 175.50432, 68.436264);
((GeneralPath)shape).curveTo(175.50232, 68.44953, 175.48712, 68.46086, 175.48521, 68.47414);
((GeneralPath)shape).curveTo(175.41072, 68.61234, 175.33813, 68.73728, 175.2769, 68.87743);
((GeneralPath)shape).curveTo(174.93867, 69.51928, 174.65, 70.10061, 173.96965, 71.41084);
((GeneralPath)shape).curveTo(174.70015, 71.8841, 175.18764, 72.90472, 175.61584, 74.05221);
((GeneralPath)shape).curveTo(175.71115, 72.94085, 175.41176, 71.83937, 174.66588, 70.916435);
((GeneralPath)shape).curveTo(178.47633, 71.651794, 181.93665, 71.16977, 184.04839, 68.605255);
((GeneralPath)shape).curveTo(184.23138, 68.3745, 184.41634, 68.13047, 184.58995, 67.87122);
((GeneralPath)shape).curveTo(183.66277, 68.738495, 182.64482, 69.022995, 180.84206, 68.65002);
((GeneralPath)shape).curveTo(180.84206, 68.65002, 180.82875, 68.64812, 180.82875, 68.64812);
((GeneralPath)shape).curveTo(180.82875, 68.64812, 180.84206, 68.65002, 180.84206, 68.65002);
((GeneralPath)shape).curveTo(183.68512, 67.846924, 185.19508, 66.88891, 186.65483, 65.16425);
((GeneralPath)shape).curveTo(186.99977, 64.75388, 187.35056, 64.303696, 187.70912, 63.800415);
((GeneralPath)shape).curveTo(185.02065, 65.85999, 182.21373, 66.13961, 179.43608, 65.20328);
((GeneralPath)shape).lineTo(177.26414, 65.12864);
((GeneralPath)shape).curveTo(177.17055, 65.30472, 177.07884, 65.46752, 176.9852, 65.6436);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_34;
g.setTransform(defaultTransform__0_0_34);
g.setClip(clip__0_0_34);
float alpha__0_0_35 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_35 = g.getClip();
AffineTransform defaultTransform__0_0_35 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_35 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(-8238.328125, -818.1522216796875), new Point2D.Double(-7698.64697265625, -818.1522216796875), new float[] {0.3233f,0.6302f,0.7514f,1.0f}, new Color[] {getColor(158, 32, 100, 255, disabled),getColor(201, 32, 55, 255, disabled),getColor(205, 35, 53, 255, disabled),getColor(233, 120, 38, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.07373976707458496f, -0.11205927282571793f, 0.11205927282571793f, 0.07373976707458496f, 859.45849609375f, -779.0554809570312f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(178.68367, 60.99827);
((GeneralPath)shape).curveTo(179.32349, 59.8719, 179.97852, 58.734203, 180.6355, 57.583237);
((GeneralPath)shape).curveTo(181.27141, 56.48341, 181.9206, 55.385536, 182.585, 54.276333);
((GeneralPath)shape).curveTo(183.24942, 53.16713, 183.9271, 52.059875, 184.60478, 50.95262);
((GeneralPath)shape).curveTo(185.29964, 49.82077, 186.0058, 48.704136, 186.72525, 47.58945);
((GeneralPath)shape).curveTo(187.42947, 46.48609, 188.16025, 45.386627, 188.8758, 44.29849);
((GeneralPath)shape).curveTo(189.1372, 43.902977, 189.39667, 43.52074, 189.67134, 43.12718);
((GeneralPath)shape).curveTo(190.1216, 42.461094, 190.58511, 41.79696, 191.04863, 41.13282);
((GeneralPath)shape).curveTo(191.06773, 41.09495, 191.10013, 41.05903, 191.11923, 41.021156);
((GeneralPath)shape).lineTo(189.26794, 35.434658);
((GeneralPath)shape).curveTo(189.21844, 35.49518, 189.1669, 35.568974, 189.10406, 35.627544);
((GeneralPath)shape).curveTo(188.34552, 36.546677, 187.57176, 37.47713, 186.82063, 38.43803);
((GeneralPath)shape).curveTo(186.0543, 39.41025, 185.29929, 40.397694, 184.55365, 41.413628);
((GeneralPath)shape).curveTo(183.92624, 42.27065, 183.3121, 43.129623, 182.69603, 44.001865);
((GeneralPath)shape).curveTo(182.57585, 44.17405, 182.45374, 44.359505, 182.33356, 44.53169);
((GeneralPath)shape).curveTo(181.58952, 45.629204, 180.88918, 46.70602, 180.263, 47.739483);
((GeneralPath)shape).curveTo(179.54904, 48.909206, 178.90923, 50.035576, 178.33224, 51.10337);
((GeneralPath)shape).curveTo(177.95773, 51.807686, 177.60039, 52.487404, 177.2735, 53.14447);
((GeneralPath)shape).curveTo(177.002, 53.7012, 176.74182, 54.273148, 176.48358, 54.83182);
((GeneralPath)shape).curveTo(175.88287, 56.153744, 175.34853, 57.485405, 174.86726, 58.824856);
((GeneralPath)shape).lineTo(176.75252, 64.457016);
((GeneralPath)shape).curveTo(177.23587, 63.565296, 177.74577, 62.677475, 178.2463, 61.761158);
((GeneralPath)shape).curveTo(178.39143, 61.511288, 178.53656, 61.261414, 178.68364, 60.99827);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_35;
g.setTransform(defaultTransform__0_0_35);
g.setClip(clip__0_0_35);
float alpha__0_0_36 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_36 = g.getClip();
AffineTransform defaultTransform__0_0_36 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_36 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(-8198.9677734375, -810.8505859375), new Point2D.Double(-7915.35009765625, -810.8505859375), new float[] {0.0f,0.0954839f,0.7882f,0.9487f}, new Color[] {getColor(40, 38, 98, 255, disabled),getColor(102, 46, 141, 255, disabled),getColor(159, 32, 100, 255, disabled),getColor(205, 32, 50, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.07373976707458496f, -0.11205927282571793f, 0.11205927282571793f, 0.07373976707458496f, 859.45849609375f, -779.0554809570312f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(174.80022, 59.00483);
((GeneralPath)shape).curveTo(174.22302, 60.628487, 173.73872, 62.265774, 173.3739, 63.920593);
((GeneralPath)shape).curveTo(173.3659, 63.973682, 173.3431, 64.0381, 173.3353, 64.09119);
((GeneralPath)shape).curveTo(172.70715, 62.64319, 170.66821, 61.0153, 170.67952, 61.03052);
((GeneralPath)shape).curveTo(171.93336, 63.573643, 172.80621, 66.033745, 172.64963, 68.302124);
((GeneralPath)shape).curveTo(171.77487, 68.35004, 170.64389, 67.926476, 169.37541, 67.23869);
((GeneralPath)shape).curveTo(170.5934, 68.73257, 171.58936, 69.244774, 171.98717, 69.398056);
((GeneralPath)shape).curveTo(170.68805, 69.28879, 169.21704, 69.98135, 167.71327, 70.8047);
((GeneralPath)shape).curveTo(169.80968, 70.28525, 171.4624, 70.202354, 172.5649, 70.635284);
((GeneralPath)shape).curveTo(169.99289, 75.50497, 167.34102, 80.91882, 164.63853, 86.677765);
((GeneralPath)shape).curveTo(165.21936, 86.60029, 165.60765, 86.26407, 165.86746, 85.78698);
((GeneralPath)shape).curveTo(166.34569, 84.74537, 169.56075, 77.89562, 174.31274, 68.98003);
((GeneralPath)shape).curveTo(174.44461, 68.7282, 174.58974, 68.47833, 174.72354, 68.21324);
((GeneralPath)shape).curveTo(174.76173, 68.137505, 174.79803, 68.075035, 174.83629, 67.99929);
((GeneralPath)shape).curveTo(175.33876, 67.0697, 175.8584, 66.11552, 176.3952, 65.136734);
((GeneralPath)shape).curveTo(176.52316, 64.91146, 176.63591, 64.69751, 176.76387, 64.47224);
((GeneralPath)shape).curveTo(176.76387, 64.47224, 176.76587, 64.45897, 176.76587, 64.45897);
((GeneralPath)shape).lineTo(174.8806, 58.826813);
((GeneralPath)shape).curveTo(174.8443, 58.889282, 174.8233, 58.940422, 174.8002, 59.004837);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_36;
g.setTransform(defaultTransform__0_0_36);
g.setClip(clip__0_0_36);
float alpha__0_0_37 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_37 = g.getClip();
AffineTransform defaultTransform__0_0_37 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_37 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(-8238.328125, -762.297119140625), new Point2D.Double(-7698.64697265625, -762.297119140625), new float[] {0.3233f,0.6302f,0.7514f,1.0f}, new Color[] {getColor(158, 32, 100, 255, disabled),getColor(201, 32, 55, 255, disabled),getColor(205, 35, 53, 255, disabled),getColor(233, 120, 38, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.07373976707458496f, -0.11205927282571793f, 0.11205927282571793f, 0.07373976707458496f, 859.45849609375f, -779.0554809570312f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(191.49104, 41.53669);
((GeneralPath)shape).curveTo(191.38805, 41.684277, 191.30025, 41.820538, 191.19725, 41.968124);
((GeneralPath)shape).curveTo(190.9015, 42.41283, 190.59248, 42.855587, 190.2948, 43.31357);
((GeneralPath)shape).curveTo(189.96472, 43.80747, 189.6327, 44.314644, 189.28545, 44.83314);
((GeneralPath)shape).curveTo(189.11183, 45.092392, 188.9382, 45.35164, 188.76263, 45.62416);
((GeneralPath)shape).curveTo(188.23787, 46.42845, 187.70921, 47.259293, 187.16339, 48.114727);
((GeneralPath)shape).curveTo(186.4935, 49.16889, 185.81776, 50.262875, 185.12097, 51.407997);
((GeneralPath)shape).curveTo(184.46045, 52.490654, 183.77888, 53.624454, 183.0934, 54.784798);
((GeneralPath)shape).curveTo(182.44228, 55.895947, 181.77396, 57.031696, 181.09787, 58.22053);
((GeneralPath)shape).curveTo(180.49629, 59.27116, 179.88885, 60.361607, 179.26427, 61.47665);
((GeneralPath)shape).curveTo(179.22997, 61.52585, 179.20697, 61.59026, 179.17256, 61.639458);
((GeneralPath)shape).curveTo(178.56319, 62.74318, 177.93471, 63.884766, 177.30232, 65.0529);
((GeneralPath)shape).curveTo(177.28513, 65.0775, 177.26802, 65.102104, 177.26413, 65.12864);
((GeneralPath)shape).lineTo(179.43607, 65.20328);
((GeneralPath)shape).curveTo(179.39816, 65.184166, 179.35837, 65.178314, 179.30724, 65.15726);
((GeneralPath)shape).curveTo(181.91557, 65.21458, 185.59378, 63.80188, 188.13042, 61.760693);
((GeneralPath)shape).curveTo(189.29881, 60.82035, 190.40158, 59.680557, 191.4711, 58.305397);
((GeneralPath)shape).curveTo(192.27177, 57.28398, 193.03687, 56.13532, 193.81013, 54.838715);
((GeneralPath)shape).curveTo(194.4765, 53.71624, 195.14516, 52.485638, 195.80286, 51.14496);
((GeneralPath)shape).curveTo(195.17407, 51.36454, 194.46407, 51.490852, 193.69743, 51.541065);
((GeneralPath)shape).curveTo(193.5608, 51.548164, 193.42418, 51.555206, 193.28757, 51.562275);
((GeneralPath)shape).curveTo(193.15094, 51.569374, 193.01628, 51.563145, 192.8664, 51.568275);
((GeneralPath)shape).lineTo(192.8664, 51.568275);
((GeneralPath)shape).lineTo(192.8664, 51.568275);
((GeneralPath)shape).curveTo(192.8664, 51.568275, 192.8664, 51.568275, 192.8664, 51.568275);
((GeneralPath)shape).curveTo(195.38983, 51.003014, 197.14865, 49.457825, 198.55638, 47.440815);
((GeneralPath)shape).curveTo(197.87025, 47.774006, 196.76646, 48.1815, 195.5045, 48.281055);
((GeneralPath)shape).curveTo(195.33939, 48.297504, 195.16295, 48.298737, 194.98651, 48.299957);
((GeneralPath)shape).curveTo(194.94672, 48.29416, 194.9049, 48.301556, 194.85184, 48.29376);
((GeneralPath)shape).lineTo(194.85184, 48.29376);
((GeneralPath)shape).lineTo(194.85184, 48.29376);
((GeneralPath)shape).curveTo(194.85184, 48.29376, 194.85184, 48.29376, 194.85184, 48.29376);
((GeneralPath)shape).curveTo(194.85184, 48.29376, 194.85184, 48.29376, 194.85184, 48.29376);
((GeneralPath)shape).lineTo(194.85184, 48.29376);
((GeneralPath)shape).curveTo(195.72537, 48.069416, 196.48529, 47.787724, 197.16399, 47.41277);
((GeneralPath)shape).curveTo(197.31035, 47.33934, 197.44537, 47.250683, 197.58041, 47.16203);
((GeneralPath)shape).curveTo(197.7896, 47.03002, 197.98746, 46.882797, 198.18532, 46.73557);
((GeneralPath)shape).curveTo(198.30902, 46.631695, 198.43079, 46.541096, 198.5545, 46.43722);
((GeneralPath)shape).curveTo(198.8343, 46.19355, 199.10474, 45.92139, 199.35251, 45.618786);
((GeneralPath)shape).curveTo(199.4344, 45.522346, 199.51445, 45.43917, 199.58311, 45.340782);
((GeneralPath)shape).curveTo(199.68611, 45.193195, 199.78717, 45.058884, 199.87497, 44.922623);
((GeneralPath)shape).curveTo(200.30415, 44.307682, 200.65724, 43.749363, 200.9475, 43.249622);
((GeneralPath)shape).curveTo(201.09262, 42.999752, 201.22058, 42.77448, 201.33333, 42.560528);
((GeneralPath)shape).curveTo(201.37354, 42.47152, 201.42503, 42.39772, 201.46518, 42.30871);
((GeneralPath)shape).curveTo(201.58182, 42.068214, 201.69456, 41.854263, 201.77686, 41.662968);
((GeneralPath)shape).curveTo(201.89934, 41.382656, 201.98749, 41.15154, 202.03935, 40.98289);
((GeneralPath)shape).lineTo(202.03935, 40.98289);
((GeneralPath)shape).lineTo(202.03935, 40.98289);
((GeneralPath)shape).curveTo(201.94995, 41.03757, 201.84734, 41.090298, 201.73337, 41.12781);
((GeneralPath)shape).curveTo(200.95433, 41.44737, 199.66156, 41.664433, 198.63885, 41.70419);
((GeneralPath)shape).lineTo(200.63434, 41.78005);
((GeneralPath)shape).lineTo(198.63885, 41.70419);
((GeneralPath)shape).curveTo(198.62555, 41.70229, 198.61226, 41.70029, 198.59706, 41.71159);
((GeneralPath)shape).curveTo(198.50217, 41.71123, 198.39409, 41.70889, 198.284, 41.71989);
((GeneralPath)shape).curveTo(198.2992, 41.70856, 198.32579, 41.71249, 198.341, 41.70113);
((GeneralPath)shape).lineTo(191.51797, 41.4457);
((GeneralPath)shape).curveTo(191.52298, 41.50074, 191.50627, 41.525333, 191.49107, 41.536663);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_37;
g.setTransform(defaultTransform__0_0_37);
g.setClip(clip__0_0_37);
float alpha__0_0_38 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_38 = g.getClip();
AffineTransform defaultTransform__0_0_38 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_38 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(-8271.8056640625, -765.0706787109375), new Point2D.Double(-7732.125, -765.0706787109375), new float[] {0.3233f,0.6302f,0.7514f,1.0f}, new Color[] {getColor(158, 32, 100, 255, disabled),getColor(201, 32, 55, 255, disabled),getColor(205, 35, 53, 255, disabled),getColor(233, 120, 38, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.07373976707458496f, -0.11205927282571793f, 0.11205927282571793f, 0.07373976707458496f, 859.45849609375f, -779.0554809570312f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(201.04672, 29.082182);
((GeneralPath)shape).curveTo(200.31508, 29.910353, 199.51088, 30.86346, 198.63605, 31.92823);
((GeneralPath)shape).curveTo(198.58644, 31.98875, 198.53885, 32.036, 198.5026, 32.098465);
((GeneralPath)shape).curveTo(197.74211, 33.03087, 196.94144, 34.052284, 196.08342, 35.187313);
((GeneralPath)shape).curveTo(195.34363, 36.16343, 194.56757, 37.202015, 193.74936, 38.342884);
((GeneralPath)shape).curveTo(193.03612, 39.322895, 192.31313, 40.36927, 191.5406, 41.476166);
((GeneralPath)shape).lineTo(198.36363, 41.731594);
((GeneralPath)shape).curveTo(200.44713, 41.115337, 201.44191, 40.434242, 202.46468, 39.377625);
((GeneralPath)shape).curveTo(202.73705, 39.09219, 203.01332, 38.78021, 203.29152, 38.454956);
((GeneralPath)shape).curveTo(204.13943, 37.481148, 204.98967, 36.399208, 205.76025, 35.305588);
((GeneralPath)shape).curveTo(206.51172, 34.249836, 207.1949, 33.19762, 207.7278, 32.24538);
((GeneralPath)shape).curveTo(208.0754, 31.632027, 208.36212, 31.063972, 208.58601, 30.554493);
((GeneralPath)shape).curveTo(208.77364, 30.10748, 208.9308, 29.683119, 209.07277, 29.27008);
((GeneralPath)shape).curveTo(206.40707, 29.32637, 203.14607, 29.28175, 201.04669, 29.082182);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_38;
g.setTransform(defaultTransform__0_0_38);
g.setClip(clip__0_0_38);
float alpha__0_0_39 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_39 = g.getClip();
AffineTransform defaultTransform__0_0_39 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_39 is ShapeNode
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(193.31021, 51.592716);
((GeneralPath)shape).curveTo(193.17358, 51.599815, 193.03893, 51.593586, 192.88904, 51.598717);
((GeneralPath)shape).lineTo(192.88904, 51.598717);
((GeneralPath)shape).curveTo(193.02567, 51.591618, 193.16228, 51.584576, 193.31021, 51.592716);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_39;
g.setTransform(defaultTransform__0_0_39);
g.setClip(clip__0_0_39);
float alpha__0_0_40 = origAlpha;
origAlpha = origAlpha * 0.35f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_40 = g.getClip();
AffineTransform defaultTransform__0_0_40 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_40 is ShapeNode
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(193.31021, 51.592716);
((GeneralPath)shape).curveTo(193.17358, 51.599815, 193.03893, 51.593586, 192.88904, 51.598717);
((GeneralPath)shape).lineTo(192.88904, 51.598717);
((GeneralPath)shape).curveTo(193.02567, 51.591618, 193.16228, 51.584576, 193.31021, 51.592716);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_40;
g.setTransform(defaultTransform__0_0_40);
g.setClip(clip__0_0_40);
float alpha__0_0_41 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_41 = g.getClip();
AffineTransform defaultTransform__0_0_41 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_41 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(-8238.328125, -745.684814453125), new Point2D.Double(-7698.64697265625, -745.684814453125), new float[] {0.3233f,0.6302f,0.7514f,1.0f}, new Color[] {getColor(158, 32, 100, 255, disabled),getColor(201, 32, 55, 255, disabled),getColor(205, 35, 53, 255, disabled),getColor(233, 120, 38, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.07373976707458496f, -0.11205927282571793f, 0.11205927282571793f, 0.07373976707458496f, 859.45849609375f, -779.0554809570312f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(193.31021, 51.592716);
((GeneralPath)shape).curveTo(193.17358, 51.599815, 193.03893, 51.593586, 192.88904, 51.598717);
((GeneralPath)shape).lineTo(192.88904, 51.598717);
((GeneralPath)shape).curveTo(193.02567, 51.591618, 193.16228, 51.584576, 193.31021, 51.592716);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_41;
g.setTransform(defaultTransform__0_0_41);
g.setClip(clip__0_0_41);
float alpha__0_0_42 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_42 = g.getClip();
AffineTransform defaultTransform__0_0_42 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_42 is ShapeNode
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(194.8764, 48.31092);
((GeneralPath)shape).curveTo(194.9162, 48.31672, 194.95801, 48.309322, 195.01108, 48.31712);
((GeneralPath)shape).curveTo(194.97128, 48.31132, 194.92947, 48.318718, 194.8764, 48.31092);
((GeneralPath)shape).lineTo(194.8764, 48.31092);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_42;
g.setTransform(defaultTransform__0_0_42);
g.setClip(clip__0_0_42);
float alpha__0_0_43 = origAlpha;
origAlpha = origAlpha * 0.35f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_43 = g.getClip();
AffineTransform defaultTransform__0_0_43 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_43 is ShapeNode
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(194.8764, 48.31092);
((GeneralPath)shape).curveTo(194.9162, 48.31672, 194.95801, 48.309322, 195.01108, 48.31712);
((GeneralPath)shape).curveTo(194.97128, 48.31132, 194.92947, 48.318718, 194.8764, 48.31092);
((GeneralPath)shape).lineTo(194.8764, 48.31092);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_43;
g.setTransform(defaultTransform__0_0_43);
g.setClip(clip__0_0_43);
float alpha__0_0_44 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_44 = g.getClip();
AffineTransform defaultTransform__0_0_44 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_44 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(-8238.328125, -747.5855712890625), new Point2D.Double(-7698.64697265625, -747.5855712890625), new float[] {0.3233f,0.6302f,0.7514f,1.0f}, new Color[] {getColor(158, 32, 100, 255, disabled),getColor(201, 32, 55, 255, disabled),getColor(205, 35, 53, 255, disabled),getColor(233, 120, 38, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.07373976707458496f, -0.11205927282571793f, 0.11205927282571793f, 0.07373976707458496f, 859.45849609375f, -779.0554809570312f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(194.8764, 48.31092);
((GeneralPath)shape).curveTo(194.9162, 48.31672, 194.95801, 48.309322, 195.01108, 48.31712);
((GeneralPath)shape).curveTo(194.97128, 48.31132, 194.92947, 48.318718, 194.8764, 48.31092);
((GeneralPath)shape).lineTo(194.8764, 48.31092);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_44;
g.setTransform(defaultTransform__0_0_44);
g.setClip(clip__0_0_44);
float alpha__0_0_45 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_45 = g.getClip();
AffineTransform defaultTransform__0_0_45 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_45 is ShapeNode
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(194.8897, 48.312866);
((GeneralPath)shape).curveTo(194.8897, 48.312866, 194.8897, 48.312866, 194.8897, 48.312866);
((GeneralPath)shape).lineTo(194.8897, 48.312866);
((GeneralPath)shape).lineTo(194.8897, 48.312866);
((GeneralPath)shape).lineTo(194.8897, 48.312866);
((GeneralPath)shape).curveTo(194.8897, 48.312866, 194.8897, 48.312866, 194.8897, 48.312866);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_45;
g.setTransform(defaultTransform__0_0_45);
g.setClip(clip__0_0_45);
float alpha__0_0_46 = origAlpha;
origAlpha = origAlpha * 0.35f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_46 = g.getClip();
AffineTransform defaultTransform__0_0_46 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_46 is ShapeNode
paint = getColor(190, 32, 46, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(194.8897, 48.312866);
((GeneralPath)shape).curveTo(194.8897, 48.312866, 194.8897, 48.312866, 194.8897, 48.312866);
((GeneralPath)shape).lineTo(194.8897, 48.312866);
((GeneralPath)shape).lineTo(194.8897, 48.312866);
((GeneralPath)shape).lineTo(194.8897, 48.312866);
((GeneralPath)shape).curveTo(194.8897, 48.312866, 194.8897, 48.312866, 194.8897, 48.312866);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_46;
g.setTransform(defaultTransform__0_0_46);
g.setClip(clip__0_0_46);
float alpha__0_0_47 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_47 = g.getClip();
AffineTransform defaultTransform__0_0_47 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_47 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(-7935.14306640625, -747.966796875), new Point2D.Double(-7815.85595703125, -747.966796875), new float[] {0.3233f,0.6302f,0.7514f,1.0f}, new Color[] {getColor(158, 32, 100, 255, disabled),getColor(201, 32, 55, 255, disabled),getColor(205, 35, 53, 255, disabled),getColor(233, 120, 38, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.07373976707458496f, -0.11205927282571793f, 0.11205927282571793f, 0.07373976707458496f, 859.45849609375f, -779.0554809570312f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(194.8897, 48.312866);
((GeneralPath)shape).curveTo(194.8897, 48.312866, 194.8897, 48.312866, 194.8897, 48.312866);
((GeneralPath)shape).lineTo(194.8897, 48.312866);
((GeneralPath)shape).lineTo(194.8897, 48.312866);
((GeneralPath)shape).lineTo(194.8897, 48.312866);
((GeneralPath)shape).curveTo(194.8897, 48.312866, 194.8897, 48.312866, 194.8897, 48.312866);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_47;
g.setTransform(defaultTransform__0_0_47);
g.setClip(clip__0_0_47);
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
        return 15;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 8;
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
	public MavenIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public MavenIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public MavenIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public MavenIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public MavenIcon(int width, int height) {
		this(width, height, false);
	}
	
	public MavenIcon(int width, int height, boolean disabled) {
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

