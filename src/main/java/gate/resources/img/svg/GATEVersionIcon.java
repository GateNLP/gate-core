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
public class GATEVersionIcon implements
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
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,60.0,60.0)));
g.setClip(clip);
// _0 is CompositeGraphicsNode
float alpha__0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0 = g.getClip();
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -116.65451049804688f, -204.4719696044922f));
// _0_0 is CompositeGraphicsNode
origAlpha = alpha__0_0;
g.setTransform(defaultTransform__0_0);
g.setClip(clip__0_0);
float alpha__0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1 = g.getClip();
AffineTransform defaultTransform__0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -116.65451049804688f, -204.4719696044922f));
// _0_1 is CompositeGraphicsNode
float alpha__0_1_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_0 = g.getClip();
AffineTransform defaultTransform__0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_0 is ShapeNode
origAlpha = alpha__0_1_0;
g.setTransform(defaultTransform__0_1_0);
g.setClip(clip__0_1_0);
float alpha__0_1_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_1 = g.getClip();
AffineTransform defaultTransform__0_1_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_1 is ShapeNode
paint = getColor(255, 255, 255, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(170.63737, 234.47195);
((GeneralPath)shape).curveTo(170.80554, 263.7357, 122.67123, 258.0005, 122.67123, 258.0005);
((GeneralPath)shape).lineTo(122.67123, 210.94342);
((GeneralPath)shape).curveTo(122.67123, 210.94342, 170.46924, 205.20825, 170.63737, 234.47195);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 128, 0, 255, disabled);
stroke = new BasicStroke(2.0333996f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(170.63737, 234.47195);
((GeneralPath)shape).curveTo(170.80554, 263.7357, 122.67123, 258.0005, 122.67123, 258.0005);
((GeneralPath)shape).lineTo(122.67123, 210.94342);
((GeneralPath)shape).curveTo(122.67123, 210.94342, 170.46924, 205.20825, 170.63737, 234.47195);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_1_1;
g.setTransform(defaultTransform__0_1_1);
g.setClip(clip__0_1_1);
float alpha__0_1_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_2 = g.getClip();
AffineTransform defaultTransform__0_1_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_2 is CompositeGraphicsNode
float alpha__0_1_2_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_2_0 = g.getClip();
AffineTransform defaultTransform__0_1_2_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_2_0 is ShapeNode
paint = getColor(255, 0, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(159.5505, 249.17284);
((GeneralPath)shape).lineTo(140.4545, 249.17284);
((GeneralPath)shape).curveTo(136.08382, 249.17284, 132.46115, 247.72084, 129.5865, 244.81683);
((GeneralPath)shape).curveTo(126.74117, 241.91283, 125.318504, 238.24617, 125.318504, 233.81683);
((GeneralPath)shape).curveTo(125.318504, 229.41685, 126.7265, 225.86752, 129.54251, 223.16884);
((GeneralPath)shape).curveTo(132.38785, 220.4702, 136.02518, 219.12086, 140.45451, 219.12083);
((GeneralPath)shape).lineTo(157.4385, 219.12083);
((GeneralPath)shape).lineTo(157.4385, 224.35684);
((GeneralPath)shape).lineTo(140.45451, 224.35684);
((GeneralPath)shape).curveTo(137.57983, 224.35686, 135.20383, 225.28087, 133.3265, 227.12885);
((GeneralPath)shape).curveTo(131.4785, 228.97687, 130.5545, 231.35286, 130.5545, 234.25685);
((GeneralPath)shape).curveTo(130.5545, 237.13153, 131.4785, 239.46352, 133.3265, 241.25285);
((GeneralPath)shape).curveTo(135.20383, 243.04219, 137.57983, 243.93686, 140.45451, 243.93686);
((GeneralPath)shape).lineTo(154.31451, 243.93686);
((GeneralPath)shape).lineTo(154.31451, 237.38086);
((GeneralPath)shape).lineTo(140.05852, 237.38086);
((GeneralPath)shape).lineTo(140.05852, 232.58485);
((GeneralPath)shape).lineTo(159.55052, 232.58485);
((GeneralPath)shape).lineTo(159.55052, 249.17285);
g.setPaint(paint);
g.fill(shape);
paint = getColor(128, 0, 0, 255, disabled);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(159.5505, 249.17284);
((GeneralPath)shape).lineTo(140.4545, 249.17284);
((GeneralPath)shape).curveTo(136.08382, 249.17284, 132.46115, 247.72084, 129.5865, 244.81683);
((GeneralPath)shape).curveTo(126.74117, 241.91283, 125.318504, 238.24617, 125.318504, 233.81683);
((GeneralPath)shape).curveTo(125.318504, 229.41685, 126.7265, 225.86752, 129.54251, 223.16884);
((GeneralPath)shape).curveTo(132.38785, 220.4702, 136.02518, 219.12086, 140.45451, 219.12083);
((GeneralPath)shape).lineTo(157.4385, 219.12083);
((GeneralPath)shape).lineTo(157.4385, 224.35684);
((GeneralPath)shape).lineTo(140.45451, 224.35684);
((GeneralPath)shape).curveTo(137.57983, 224.35686, 135.20383, 225.28087, 133.3265, 227.12885);
((GeneralPath)shape).curveTo(131.4785, 228.97687, 130.5545, 231.35286, 130.5545, 234.25685);
((GeneralPath)shape).curveTo(130.5545, 237.13153, 131.4785, 239.46352, 133.3265, 241.25285);
((GeneralPath)shape).curveTo(135.20383, 243.04219, 137.57983, 243.93686, 140.45451, 243.93686);
((GeneralPath)shape).lineTo(154.31451, 243.93686);
((GeneralPath)shape).lineTo(154.31451, 237.38086);
((GeneralPath)shape).lineTo(140.05852, 237.38086);
((GeneralPath)shape).lineTo(140.05852, 232.58485);
((GeneralPath)shape).lineTo(159.55052, 232.58485);
((GeneralPath)shape).lineTo(159.55052, 249.17285);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_1_2_0;
g.setTransform(defaultTransform__0_1_2_0);
g.setClip(clip__0_1_2_0);
origAlpha = alpha__0_1_2;
g.setTransform(defaultTransform__0_1_2);
g.setClip(clip__0_1_2);
float alpha__0_1_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_3 = g.getClip();
AffineTransform defaultTransform__0_1_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_3 is ShapeNode
paint = getColor(255, 255, 255, 255, disabled);
shape = new Rectangle2D.Double(151.00753784179688, 250.3676300048828, 25.646968841552734, 14.104340553283691);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1_3;
g.setTransform(defaultTransform__0_1_3);
g.setClip(clip__0_1_3);
float alpha__0_1_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_4 = g.getClip();
AffineTransform defaultTransform__0_1_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_4 is TextNode of '8.5'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(156.65524, 258.03308);
((GeneralPath)shape).quadTo(155.8115, 258.03308, 155.35837, 258.49402);
((GeneralPath)shape).quadTo(154.90524, 258.95496, 154.90524, 259.81433);
((GeneralPath)shape).quadTo(154.90524, 260.6737, 155.35837, 261.13074);
((GeneralPath)shape).quadTo(155.8115, 261.58777, 156.65524, 261.58777);
((GeneralPath)shape).quadTo(157.49118, 261.58777, 157.9365, 261.13074);
((GeneralPath)shape).quadTo(158.3818, 260.6737, 158.3818, 259.81433);
((GeneralPath)shape).quadTo(158.3818, 258.94714, 157.9365, 258.4901);
((GeneralPath)shape).quadTo(157.49118, 258.03308, 156.65524, 258.03308);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(154.45993, 257.0409);
((GeneralPath)shape).quadTo(153.39743, 256.72058, 152.85837, 256.05652);
((GeneralPath)shape).quadTo(152.3193, 255.39246, 152.3193, 254.40027);
((GeneralPath)shape).quadTo(152.3193, 252.9237, 153.42087, 252.15027);
((GeneralPath)shape).quadTo(154.52243, 251.37683, 156.65524, 251.37683);
((GeneralPath)shape).quadTo(158.77243, 251.37683, 159.874, 252.14636);
((GeneralPath)shape).quadTo(160.97556, 252.9159, 160.97556, 254.40027);
((GeneralPath)shape).quadTo(160.97556, 255.39246, 160.43259, 256.05652);
((GeneralPath)shape).quadTo(159.88962, 256.72058, 158.82712, 257.0409);
((GeneralPath)shape).quadTo(160.01462, 257.36902, 160.62009, 258.1073);
((GeneralPath)shape).quadTo(161.22556, 258.84558, 161.22556, 259.97058);
((GeneralPath)shape).quadTo(161.22556, 261.70496, 160.07321, 262.59167);
((GeneralPath)shape).quadTo(158.92087, 263.4784, 156.65524, 263.4784);
((GeneralPath)shape).quadTo(154.3818, 263.4784, 153.22165, 262.59167);
((GeneralPath)shape).quadTo(152.0615, 261.70496, 152.0615, 259.97058);
((GeneralPath)shape).quadTo(152.0615, 258.84558, 152.66696, 258.1073);
((GeneralPath)shape).quadTo(153.27243, 257.36902, 154.45993, 257.0409);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(155.16306, 254.70496);
((GeneralPath)shape).quadTo(155.16306, 255.40027, 155.54977, 255.77527);
((GeneralPath)shape).quadTo(155.9365, 256.15027, 156.65524, 256.15027);
((GeneralPath)shape).quadTo(157.35837, 256.15027, 157.74118, 255.77527);
((GeneralPath)shape).quadTo(158.124, 255.40027, 158.124, 254.70496);
((GeneralPath)shape).quadTo(158.124, 254.00964, 157.74118, 253.63855);
((GeneralPath)shape).quadTo(157.35837, 253.26746, 156.65524, 253.26746);
((GeneralPath)shape).quadTo(155.9365, 253.26746, 155.54977, 253.64246);
((GeneralPath)shape).quadTo(155.16306, 254.01746, 155.16306, 254.70496);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(162.35056, 260.2284);
((GeneralPath)shape).lineTo(165.16306, 260.2284);
((GeneralPath)shape).lineTo(165.16306, 263.25183);
((GeneralPath)shape).lineTo(162.35056, 263.25183);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(166.99118, 251.58777);
((GeneralPath)shape).lineTo(174.46774, 251.58777);
((GeneralPath)shape).lineTo(174.46774, 253.7987);
((GeneralPath)shape).lineTo(169.38962, 253.7987);
((GeneralPath)shape).lineTo(169.38962, 255.6034);
((GeneralPath)shape).quadTo(169.73337, 255.50964, 170.08102, 255.45886);
((GeneralPath)shape).quadTo(170.42868, 255.40808, 170.80368, 255.40808);
((GeneralPath)shape).quadTo(172.9365, 255.40808, 174.124, 256.4745);
((GeneralPath)shape).quadTo(175.3115, 257.5409, 175.3115, 259.44714);
((GeneralPath)shape).quadTo(175.3115, 261.33777, 174.01852, 262.40808);
((GeneralPath)shape).quadTo(172.72556, 263.4784, 170.42868, 263.4784);
((GeneralPath)shape).quadTo(169.4365, 263.4784, 168.46384, 263.287);
((GeneralPath)shape).quadTo(167.49118, 263.09558, 166.53024, 262.70496);
((GeneralPath)shape).lineTo(166.53024, 260.33777);
((GeneralPath)shape).quadTo(167.48337, 260.88464, 168.33884, 261.15808);
((GeneralPath)shape).quadTo(169.1943, 261.43152, 169.95212, 261.43152);
((GeneralPath)shape).quadTo(171.04587, 261.43152, 171.67477, 260.89636);
((GeneralPath)shape).quadTo(172.30368, 260.3612, 172.30368, 259.44714);
((GeneralPath)shape).quadTo(172.30368, 258.52527, 171.67477, 257.99402);
((GeneralPath)shape).quadTo(171.04587, 257.46277, 169.95212, 257.46277);
((GeneralPath)shape).quadTo(169.30368, 257.46277, 168.5693, 257.63074);
((GeneralPath)shape).quadTo(167.83493, 257.7987, 166.99118, 258.15027);
((GeneralPath)shape).closePath();
paint = getColor(0, 128, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 128, 0, 255, disabled);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
g.setStroke(stroke);
g.setPaint(paint);
g.draw(shape);
origAlpha = alpha__0_1_4;
g.setTransform(defaultTransform__0_1_4);
g.setClip(clip__0_1_4);
float alpha__0_1_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_5 = g.getClip();
AffineTransform defaultTransform__0_1_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_5 is CompositeGraphicsNode
float alpha__0_1_5_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_5_0 = g.getClip();
AffineTransform defaultTransform__0_1_5_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_5_0 is ShapeNode
paint = getColor(255, 255, 255, 255, disabled);
shape = new Rectangle2D.Double(116.65451049804688, 255.174072265625, 35.33734893798828, 9.297898292541504);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1_5_0;
g.setTransform(defaultTransform__0_1_5_0);
g.setClip(clip__0_1_5_0);
float alpha__0_1_5_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_5_1 = g.getClip();
AffineTransform defaultTransform__0_1_5_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_5_1 is TextNode of 'alpha1'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(119.49457, 262.1903);
((GeneralPath)shape).quadTo(119.16644, 262.1903, 119.000916, 262.30164);
((GeneralPath)shape).quadTo(118.83539, 262.41296, 118.83539, 262.62976);
((GeneralPath)shape).quadTo(118.83539, 262.82898, 118.96869, 262.94177);
((GeneralPath)shape).quadTo(119.10199, 263.05457, 119.339294, 263.05457);
((GeneralPath)shape).quadTo(119.63519, 263.05457, 119.83734, 262.84216);
((GeneralPath)shape).quadTo(120.03949, 262.62976, 120.03949, 262.31042);
((GeneralPath)shape).lineTo(120.03949, 262.1903);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(121.09711, 261.7948);
((GeneralPath)shape).lineTo(121.09711, 263.66687);
((GeneralPath)shape).lineTo(120.03949, 263.66687);
((GeneralPath)shape).lineTo(120.03949, 263.18054);
((GeneralPath)shape).quadTo(119.82855, 263.47937, 119.56488, 263.6156);
((GeneralPath)shape).quadTo(119.30121, 263.75183, 118.92328, 263.75183);
((GeneralPath)shape).quadTo(118.41351, 263.75183, 118.09564, 263.45447);
((GeneralPath)shape).quadTo(117.77777, 263.1571, 117.77777, 262.6825);
((GeneralPath)shape).quadTo(117.77777, 262.10535, 118.17474, 261.83582);
((GeneralPath)shape).quadTo(118.57172, 261.56628, 119.421326, 261.56628);
((GeneralPath)shape).lineTo(120.03949, 261.56628);
((GeneralPath)shape).lineTo(120.03949, 261.48425);
((GeneralPath)shape).quadTo(120.03949, 261.23523, 119.8432, 261.1195);
((GeneralPath)shape).quadTo(119.64691, 261.00378, 119.230896, 261.00378);
((GeneralPath)shape).quadTo(118.89398, 261.00378, 118.60394, 261.07117);
((GeneralPath)shape).quadTo(118.3139, 261.13855, 118.06488, 261.27332);
((GeneralPath)shape).lineTo(118.06488, 260.4735);
((GeneralPath)shape).quadTo(118.401794, 260.39148, 118.74164, 260.349);
((GeneralPath)shape).quadTo(119.08148, 260.30652, 119.421326, 260.30652);
((GeneralPath)shape).quadTo(120.30902, 260.30652, 120.703064, 260.65662);
((GeneralPath)shape).quadTo(121.09711, 261.0067, 121.09711, 261.7948);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(121.57269, 259.10828);
((GeneralPath)shape).lineTo(122.62152, 259.10828);
((GeneralPath)shape).lineTo(122.62152, 263.66687);
((GeneralPath)shape).lineTo(121.57269, 263.66687);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(124.17816, 263.19226);
((GeneralPath)shape).lineTo(124.17816, 264.91492);
((GeneralPath)shape).lineTo(123.12933, 264.91492);
((GeneralPath)shape).lineTo(123.12933, 260.38562);
((GeneralPath)shape).lineTo(124.17816, 260.38562);
((GeneralPath)shape).lineTo(124.17816, 260.8661);
((GeneralPath)shape).quadTo(124.39496, 260.57898, 124.65863, 260.44275);
((GeneralPath)shape).quadTo(124.9223, 260.30652, 125.265076, 260.30652);
((GeneralPath)shape).quadTo(125.87152, 260.30652, 126.26117, 260.78845);
((GeneralPath)shape).quadTo(126.65082, 261.2704, 126.65082, 262.02917);
((GeneralPath)shape).quadTo(126.65082, 262.78796, 126.26117, 263.2699);
((GeneralPath)shape).quadTo(125.87152, 263.75183, 125.265076, 263.75183);
((GeneralPath)shape).quadTo(124.9223, 263.75183, 124.65863, 263.6156);
((GeneralPath)shape).quadTo(124.39496, 263.47937, 124.17816, 263.19226);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(124.87543, 261.06824);
((GeneralPath)shape).quadTo(124.53851, 261.06824, 124.35834, 261.3158);
((GeneralPath)shape).quadTo(124.17816, 261.56335, 124.17816, 262.02917);
((GeneralPath)shape).quadTo(124.17816, 262.495, 124.35834, 262.74255);
((GeneralPath)shape).quadTo(124.53851, 262.9901, 124.87543, 262.9901);
((GeneralPath)shape).quadTo(125.21234, 262.9901, 125.38959, 262.74402);
((GeneralPath)shape).quadTo(125.56683, 262.49792, 125.56683, 262.02917);
((GeneralPath)shape).quadTo(125.56683, 261.56042, 125.38959, 261.31433);
((GeneralPath)shape).quadTo(125.21234, 261.06824, 124.87543, 261.06824);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(130.22308, 261.66882);
((GeneralPath)shape).lineTo(130.22308, 263.66687);
((GeneralPath)shape).lineTo(129.1684, 263.66687);
((GeneralPath)shape).lineTo(129.1684, 263.34167);
((GeneralPath)shape).lineTo(129.1684, 262.14343);
((GeneralPath)shape).quadTo(129.1684, 261.71277, 129.14935, 261.55164);
((GeneralPath)shape).quadTo(129.13031, 261.3905, 129.08344, 261.31433);
((GeneralPath)shape).quadTo(129.02191, 261.2118, 128.91644, 261.15466);
((GeneralPath)shape).quadTo(128.81097, 261.09753, 128.67621, 261.09753);
((GeneralPath)shape).quadTo(128.34808, 261.09753, 128.16058, 261.35095);
((GeneralPath)shape).quadTo(127.97308, 261.60437, 127.97308, 262.0526);
((GeneralPath)shape).lineTo(127.97308, 263.66687);
((GeneralPath)shape).lineTo(126.924255, 263.66687);
((GeneralPath)shape).lineTo(126.924255, 259.10828);
((GeneralPath)shape).lineTo(127.97308, 259.10828);
((GeneralPath)shape).lineTo(127.97308, 260.8661);
((GeneralPath)shape).quadTo(128.21039, 260.57898, 128.47699, 260.44275);
((GeneralPath)shape).quadTo(128.74359, 260.30652, 129.06586, 260.30652);
((GeneralPath)shape).quadTo(129.63422, 260.30652, 129.92865, 260.65515);
((GeneralPath)shape).quadTo(130.22308, 261.00378, 130.22308, 261.66882);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(132.16644, 262.1903);
((GeneralPath)shape).quadTo(131.83832, 262.1903, 131.67279, 262.30164);
((GeneralPath)shape).quadTo(131.50726, 262.41296, 131.50726, 262.62976);
((GeneralPath)shape).quadTo(131.50726, 262.82898, 131.64056, 262.94177);
((GeneralPath)shape).quadTo(131.77386, 263.05457, 132.01117, 263.05457);
((GeneralPath)shape).quadTo(132.30707, 263.05457, 132.50922, 262.84216);
((GeneralPath)shape).quadTo(132.71136, 262.62976, 132.71136, 262.31042);
((GeneralPath)shape).lineTo(132.71136, 262.1903);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(133.76898, 261.7948);
((GeneralPath)shape).lineTo(133.76898, 263.66687);
((GeneralPath)shape).lineTo(132.71136, 263.66687);
((GeneralPath)shape).lineTo(132.71136, 263.18054);
((GeneralPath)shape).quadTo(132.50043, 263.47937, 132.23676, 263.6156);
((GeneralPath)shape).quadTo(131.97308, 263.75183, 131.59515, 263.75183);
((GeneralPath)shape).quadTo(131.08539, 263.75183, 130.76752, 263.45447);
((GeneralPath)shape).quadTo(130.44965, 263.1571, 130.44965, 262.6825);
((GeneralPath)shape).quadTo(130.44965, 262.10535, 130.84662, 261.83582);
((GeneralPath)shape).quadTo(131.24359, 261.56628, 132.0932, 261.56628);
((GeneralPath)shape).lineTo(132.71136, 261.56628);
((GeneralPath)shape).lineTo(132.71136, 261.48425);
((GeneralPath)shape).quadTo(132.71136, 261.23523, 132.51508, 261.1195);
((GeneralPath)shape).quadTo(132.31879, 261.00378, 131.90277, 261.00378);
((GeneralPath)shape).quadTo(131.56586, 261.00378, 131.27582, 261.07117);
((GeneralPath)shape).quadTo(130.98578, 261.13855, 130.73676, 261.27332);
((GeneralPath)shape).lineTo(130.73676, 260.4735);
((GeneralPath)shape).quadTo(131.07367, 260.39148, 131.41351, 260.349);
((GeneralPath)shape).quadTo(131.75336, 260.30652, 132.0932, 260.30652);
((GeneralPath)shape).quadTo(132.9809, 260.30652, 133.37494, 260.65662);
((GeneralPath)shape).quadTo(133.76898, 261.0067, 133.76898, 261.7948);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(134.44379, 262.88757);
((GeneralPath)shape).lineTo(135.43988, 262.88757);
((GeneralPath)shape).lineTo(135.43988, 260.06042);
((GeneralPath)shape).lineTo(134.41742, 260.27136);
((GeneralPath)shape).lineTo(134.41742, 259.50378);
((GeneralPath)shape).lineTo(135.43402, 259.29285);
((GeneralPath)shape).lineTo(136.50629, 259.29285);
((GeneralPath)shape).lineTo(136.50629, 262.88757);
((GeneralPath)shape).lineTo(137.50238, 262.88757);
((GeneralPath)shape).lineTo(137.50238, 263.66687);
((GeneralPath)shape).lineTo(134.44379, 263.66687);
((GeneralPath)shape).closePath();
paint = getColor(0, 128, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1_5_1;
g.setTransform(defaultTransform__0_1_5_1);
g.setClip(clip__0_1_5_1);
origAlpha = alpha__0_1_5;
g.setTransform(defaultTransform__0_1_5);
g.setClip(clip__0_1_5);
origAlpha = alpha__0_1;
g.setTransform(defaultTransform__0_1);
g.setClip(clip__0_1);
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
		return 60;
	}

	/**
	 * Returns the height of the bounding box of the original SVG image.
	 * 
	 * @return The height of the bounding box of the original SVG image.
	 */
	public static int getOrigHeight() {
		return 60;
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
	public GATEVersionIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public GATEVersionIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public GATEVersionIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public GATEVersionIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public GATEVersionIcon(int width, int height) {
		this(width, height, false);
	}
	
	public GATEVersionIcon(int width, int height, boolean disabled) {
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

