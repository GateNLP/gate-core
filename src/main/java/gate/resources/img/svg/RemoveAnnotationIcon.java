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
public class RemoveAnnotationIcon implements
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
g.transform(new AffineTransform(1.4053070545196533f, 0.2179269939661026f, -0.3918989896774292f, 0.6508150100708008f, 0.0f, 0.0f));
// _0_0_0 is TextNode of 'annotation'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(23.182325, 70.4855);
((GeneralPath)shape).quadTo(22.402899, 70.4855, 22.101864, 70.66402);
((GeneralPath)shape).quadTo(21.800829, 70.84254, 21.800829, 71.27426);
((GeneralPath)shape).quadTo(21.800829, 71.6173, 22.026022, 71.817986);
((GeneralPath)shape).quadTo(22.251215, 72.01868, 22.640926, 72.01868);
((GeneralPath)shape).quadTo(23.175323, 72.01868, 23.499695, 71.639465);
((GeneralPath)shape).quadTo(23.824066, 71.260254, 23.824066, 70.63018);
((GeneralPath)shape).lineTo(23.824066, 70.4855);
((GeneralPath)shape).lineTo(23.182325, 70.4855);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(24.468143, 70.21947);
((GeneralPath)shape).lineTo(24.468143, 72.4574);
((GeneralPath)shape).lineTo(23.824066, 72.4574);
((GeneralPath)shape).lineTo(23.824066, 71.86233);
((GeneralPath)shape).quadTo(23.602373, 72.21937, 23.273335, 72.389725);
((GeneralPath)shape).quadTo(22.944296, 72.560074, 22.46824, 72.560074);
((GeneralPath)shape).quadTo(21.866169, 72.560074, 21.511461, 72.2217);
((GeneralPath)shape).quadTo(21.156752, 71.88333, 21.156752, 71.31626);
((GeneralPath)shape).quadTo(21.156752, 70.65352, 21.59897, 70.31748);
((GeneralPath)shape).quadTo(22.04119, 69.98144, 22.92096, 69.98144);
((GeneralPath)shape).lineTo(23.824066, 69.98144);
((GeneralPath)shape).lineTo(23.824066, 69.918434);
((GeneralPath)shape).quadTo(23.824066, 69.475044, 23.531199, 69.231186);
((GeneralPath)shape).quadTo(23.23833, 68.98732, 22.710936, 68.98732);
((GeneralPath)shape).quadTo(22.374895, 68.98732, 22.056358, 69.06783);
((GeneralPath)shape).quadTo(21.737822, 69.14834, 21.443787, 69.30936);
((GeneralPath)shape).lineTo(21.443787, 68.71429);
((GeneralPath)shape).quadTo(21.796162, 68.57894, 22.1287, 68.5101);
((GeneralPath)shape).quadTo(22.461239, 68.44125, 22.776276, 68.44125);
((GeneralPath)shape).quadTo(23.628044, 68.44125, 24.048094, 68.88231);
((GeneralPath)shape).quadTo(24.468143, 69.323364, 24.468143, 70.21947);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(29.052517, 70.09112);
((GeneralPath)shape).lineTo(29.052517, 72.4574);
((GeneralPath)shape).lineTo(28.408442, 72.4574);
((GeneralPath)shape).lineTo(28.408442, 70.11212);
((GeneralPath)shape).quadTo(28.408442, 69.55439, 28.191416, 69.277855);
((GeneralPath)shape).quadTo(27.97439, 69.00132, 27.540339, 69.00132);
((GeneralPath)shape).quadTo(27.019945, 69.00132, 26.718908, 69.33386);
((GeneralPath)shape).quadTo(26.417873, 69.666405, 26.417873, 70.24047);
((GeneralPath)shape).lineTo(26.417873, 72.4574);
((GeneralPath)shape).lineTo(25.76913, 72.4574);
((GeneralPath)shape).lineTo(25.76913, 68.536934);
((GeneralPath)shape).lineTo(26.417873, 68.536934);
((GeneralPath)shape).lineTo(26.417873, 69.146);
((GeneralPath)shape).quadTo(26.648901, 68.7913, 26.961603, 68.61628);
((GeneralPath)shape).quadTo(27.274307, 68.44125, 27.685022, 68.44125);
((GeneralPath)shape).quadTo(28.359436, 68.44125, 28.705976, 68.86014);
((GeneralPath)shape).quadTo(29.052517, 69.27902, 29.052517, 70.09112);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(33.596054, 70.09112);
((GeneralPath)shape).lineTo(33.596054, 72.4574);
((GeneralPath)shape).lineTo(32.951977, 72.4574);
((GeneralPath)shape).lineTo(32.951977, 70.11212);
((GeneralPath)shape).quadTo(32.951977, 69.55439, 32.73495, 69.277855);
((GeneralPath)shape).quadTo(32.517925, 69.00132, 32.083874, 69.00132);
((GeneralPath)shape).quadTo(31.56348, 69.00132, 31.262444, 69.33386);
((GeneralPath)shape).quadTo(30.961409, 69.666405, 30.961409, 70.24047);
((GeneralPath)shape).lineTo(30.961409, 72.4574);
((GeneralPath)shape).lineTo(30.312666, 72.4574);
((GeneralPath)shape).lineTo(30.312666, 68.536934);
((GeneralPath)shape).lineTo(30.961409, 68.536934);
((GeneralPath)shape).lineTo(30.961409, 69.146);
((GeneralPath)shape).quadTo(31.192436, 68.7913, 31.505138, 68.61628);
((GeneralPath)shape).quadTo(31.817842, 68.44125, 32.228558, 68.44125);
((GeneralPath)shape).quadTo(32.90297, 68.44125, 33.24951, 68.86014);
((GeneralPath)shape).quadTo(33.596054, 69.27902, 33.596054, 70.09112);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(36.40105, 68.98732);
((GeneralPath)shape).quadTo(35.882988, 68.98732, 35.58195, 69.392204);
((GeneralPath)shape).quadTo(35.28092, 69.79708, 35.28092, 70.4995);
((GeneralPath)shape).quadTo(35.28092, 71.20425, 35.57962, 71.60796);
((GeneralPath)shape).quadTo(35.878323, 72.01168, 36.40105, 72.01168);
((GeneralPath)shape).quadTo(36.914444, 72.01168, 37.215477, 71.60563);
((GeneralPath)shape).quadTo(37.516514, 71.199585, 37.516514, 70.4995);
((GeneralPath)shape).quadTo(37.516514, 69.804085, 37.215477, 69.395706);
((GeneralPath)shape).quadTo(36.914444, 68.98732, 36.40105, 68.98732);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(36.40105, 68.44125);
((GeneralPath)shape).quadTo(37.24115, 68.44125, 37.720707, 68.98732);
((GeneralPath)shape).quadTo(38.20026, 69.53339, 38.20026, 70.4995);
((GeneralPath)shape).quadTo(38.20026, 71.46328, 37.720707, 72.01168);
((GeneralPath)shape).quadTo(37.24115, 72.560074, 36.40105, 72.560074);
((GeneralPath)shape).quadTo(35.556282, 72.560074, 35.07906, 72.01168);
((GeneralPath)shape).quadTo(34.601837, 71.46328, 34.601837, 70.4995);
((GeneralPath)shape).quadTo(34.601837, 69.53339, 35.07906, 68.98732);
((GeneralPath)shape).quadTo(35.556282, 68.44125, 36.40105, 68.44125);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(39.90496, 67.423805);
((GeneralPath)shape).lineTo(39.90496, 68.536934);
((GeneralPath)shape).lineTo(41.230453, 68.536934);
((GeneralPath)shape).lineTo(41.230453, 69.03633);
((GeneralPath)shape).lineTo(39.90496, 69.03633);
((GeneralPath)shape).lineTo(39.90496, 71.16458);
((GeneralPath)shape).quadTo(39.90496, 71.6453, 40.035645, 71.781815);
((GeneralPath)shape).quadTo(40.166325, 71.918335, 40.57004, 71.918335);
((GeneralPath)shape).lineTo(41.230453, 71.918335);
((GeneralPath)shape).lineTo(41.230453, 72.4574);
((GeneralPath)shape).lineTo(40.57004, 72.4574);
((GeneralPath)shape).quadTo(39.823284, 72.4574, 39.539753, 72.17853);
((GeneralPath)shape).quadTo(39.256218, 71.899666, 39.256218, 71.16458);
((GeneralPath)shape).lineTo(39.256218, 69.03633);
((GeneralPath)shape).lineTo(38.784832, 69.03633);
((GeneralPath)shape).lineTo(38.784832, 68.536934);
((GeneralPath)shape).lineTo(39.256218, 68.536934);
((GeneralPath)shape).lineTo(39.256218, 67.423805);
((GeneralPath)shape).lineTo(39.90496, 67.423805);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(43.85926, 70.4855);
((GeneralPath)shape).quadTo(43.079834, 70.4855, 42.778797, 70.66402);
((GeneralPath)shape).quadTo(42.477764, 70.84254, 42.477764, 71.27426);
((GeneralPath)shape).quadTo(42.477764, 71.6173, 42.702957, 71.817986);
((GeneralPath)shape).quadTo(42.92815, 72.01868, 43.317863, 72.01868);
((GeneralPath)shape).quadTo(43.85226, 72.01868, 44.176632, 71.639465);
((GeneralPath)shape).quadTo(44.501003, 71.260254, 44.501003, 70.63018);
((GeneralPath)shape).lineTo(44.501003, 70.4855);
((GeneralPath)shape).lineTo(43.85926, 70.4855);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(45.145077, 70.21947);
((GeneralPath)shape).lineTo(45.145077, 72.4574);
((GeneralPath)shape).lineTo(44.501003, 72.4574);
((GeneralPath)shape).lineTo(44.501003, 71.86233);
((GeneralPath)shape).quadTo(44.27931, 72.21937, 43.95027, 72.389725);
((GeneralPath)shape).quadTo(43.62123, 72.560074, 43.145176, 72.560074);
((GeneralPath)shape).quadTo(42.543106, 72.560074, 42.188396, 72.2217);
((GeneralPath)shape).quadTo(41.833687, 71.88333, 41.833687, 71.31626);
((GeneralPath)shape).quadTo(41.833687, 70.65352, 42.275906, 70.31748);
((GeneralPath)shape).quadTo(42.718124, 69.98144, 43.597897, 69.98144);
((GeneralPath)shape).lineTo(44.501003, 69.98144);
((GeneralPath)shape).lineTo(44.501003, 69.918434);
((GeneralPath)shape).quadTo(44.501003, 69.475044, 44.208134, 69.231186);
((GeneralPath)shape).quadTo(43.915268, 68.98732, 43.38787, 68.98732);
((GeneralPath)shape).quadTo(43.05183, 68.98732, 42.733295, 69.06783);
((GeneralPath)shape).quadTo(42.414757, 69.14834, 42.12072, 69.30936);
((GeneralPath)shape).lineTo(42.12072, 68.71429);
((GeneralPath)shape).quadTo(42.473095, 68.57894, 42.805634, 68.5101);
((GeneralPath)shape).quadTo(43.138176, 68.44125, 43.453213, 68.44125);
((GeneralPath)shape).quadTo(44.304977, 68.44125, 44.72503, 68.88231);
((GeneralPath)shape).quadTo(45.145077, 69.323364, 45.145077, 70.21947);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(47.10881, 67.423805);
((GeneralPath)shape).lineTo(47.10881, 68.536934);
((GeneralPath)shape).lineTo(48.434303, 68.536934);
((GeneralPath)shape).lineTo(48.434303, 69.03633);
((GeneralPath)shape).lineTo(47.10881, 69.03633);
((GeneralPath)shape).lineTo(47.10881, 71.16458);
((GeneralPath)shape).quadTo(47.10881, 71.6453, 47.239494, 71.781815);
((GeneralPath)shape).quadTo(47.370174, 71.918335, 47.77389, 71.918335);
((GeneralPath)shape).lineTo(48.434303, 71.918335);
((GeneralPath)shape).lineTo(48.434303, 72.4574);
((GeneralPath)shape).lineTo(47.77389, 72.4574);
((GeneralPath)shape).quadTo(47.027134, 72.4574, 46.743603, 72.17853);
((GeneralPath)shape).quadTo(46.460068, 71.899666, 46.460068, 71.16458);
((GeneralPath)shape).lineTo(46.460068, 69.03633);
((GeneralPath)shape).lineTo(45.98868, 69.03633);
((GeneralPath)shape).lineTo(45.98868, 68.536934);
((GeneralPath)shape).lineTo(46.460068, 68.536934);
((GeneralPath)shape).lineTo(46.460068, 67.423805);
((GeneralPath)shape).lineTo(47.10881, 67.423805);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(49.282566, 68.536934);
((GeneralPath)shape).lineTo(49.926643, 68.536934);
((GeneralPath)shape).lineTo(49.926643, 72.4574);
((GeneralPath)shape).lineTo(49.282566, 72.4574);
((GeneralPath)shape).lineTo(49.282566, 68.536934);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(49.282566, 67.01076);
((GeneralPath)shape).lineTo(49.926643, 67.01076);
((GeneralPath)shape).lineTo(49.926643, 67.82519);
((GeneralPath)shape).lineTo(49.282566, 67.82519);
((GeneralPath)shape).lineTo(49.282566, 67.01076);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(52.793484, 68.98732);
((GeneralPath)shape).quadTo(52.27542, 68.98732, 51.974384, 69.392204);
((GeneralPath)shape).quadTo(51.67335, 69.79708, 51.67335, 70.4995);
((GeneralPath)shape).quadTo(51.67335, 71.20425, 51.972054, 71.60796);
((GeneralPath)shape).quadTo(52.270756, 72.01168, 52.793484, 72.01168);
((GeneralPath)shape).quadTo(53.306877, 72.01168, 53.60791, 71.60563);
((GeneralPath)shape).quadTo(53.908947, 71.199585, 53.908947, 70.4995);
((GeneralPath)shape).quadTo(53.908947, 69.804085, 53.60791, 69.395706);
((GeneralPath)shape).quadTo(53.306877, 68.98732, 52.793484, 68.98732);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(52.793484, 68.44125);
((GeneralPath)shape).quadTo(53.633583, 68.44125, 54.11314, 68.98732);
((GeneralPath)shape).quadTo(54.592693, 69.53339, 54.592693, 70.4995);
((GeneralPath)shape).quadTo(54.592693, 71.46328, 54.11314, 72.01168);
((GeneralPath)shape).quadTo(53.633583, 72.560074, 52.793484, 72.560074);
((GeneralPath)shape).quadTo(51.948715, 72.560074, 51.471493, 72.01168);
((GeneralPath)shape).quadTo(50.99427, 71.46328, 50.99427, 70.4995);
((GeneralPath)shape).quadTo(50.99427, 69.53339, 51.471493, 68.98732);
((GeneralPath)shape).quadTo(51.948715, 68.44125, 52.793484, 68.44125);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(58.918037, 70.09112);
((GeneralPath)shape).lineTo(58.918037, 72.4574);
((GeneralPath)shape).lineTo(58.27396, 72.4574);
((GeneralPath)shape).lineTo(58.27396, 70.11212);
((GeneralPath)shape).quadTo(58.27396, 69.55439, 58.056934, 69.277855);
((GeneralPath)shape).quadTo(57.839912, 69.00132, 57.40586, 69.00132);
((GeneralPath)shape).quadTo(56.885464, 69.00132, 56.58443, 69.33386);
((GeneralPath)shape).quadTo(56.283394, 69.666405, 56.283394, 70.24047);
((GeneralPath)shape).lineTo(56.283394, 72.4574);
((GeneralPath)shape).lineTo(55.63465, 72.4574);
((GeneralPath)shape).lineTo(55.63465, 68.536934);
((GeneralPath)shape).lineTo(56.283394, 68.536934);
((GeneralPath)shape).lineTo(56.283394, 69.146);
((GeneralPath)shape).quadTo(56.51442, 68.7913, 56.827126, 68.61628);
((GeneralPath)shape).quadTo(57.139828, 68.44125, 57.550545, 68.44125);
((GeneralPath)shape).quadTo(58.224957, 68.44125, 58.571495, 68.86014);
((GeneralPath)shape).quadTo(58.918037, 69.27902, 58.918037, 70.09112);
((GeneralPath)shape).closePath();
paint = getColor(0, 0, 0, 255, disabled);
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
g.transform(new AffineTransform(0.9840229749679565f, 0.17804299294948578f, -0.34132200479507446f, 0.9399459958076477f, 0.0f, 0.0f));
// _0_0_1 is ShapeNode
paint = getColor(0, 155, 0, 159, disabled);
shape = new Rectangle2D.Double(40.02396774291992, 45.3763542175293, 33.6992301940918, 5.556447982788086);
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
g.transform(new AffineTransform(0.7213749885559082f, 0.5638329982757568f, -0.556518018245697f, 0.7308580279350281f, 26.152950286865234f, -22.86850929260254f));
// _0_0_2 is CompositeGraphicsNode
float alpha__0_0_2_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_0 = g.getClip();
AffineTransform defaultTransform__0_0_2_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_0 is ShapeNode
paint = getColor(0, 155, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(34.50621, 6.5062113);
((GeneralPath)shape).lineTo(34.50621, 57.50621);
((GeneralPath)shape).lineTo(34.59996, 57.50621);
((GeneralPath)shape).curveTo(34.590523, 57.581318, 34.50621, 57.64886, 34.50621, 57.72496);
((GeneralPath)shape).curveTo(34.50621, 59.678455, 37.72298, 61.233765, 41.97496, 61.69371);
((GeneralPath)shape).lineTo(41.97496, 71.81871);
((GeneralPath)shape).lineTo(47.59996, 67.81871);
((GeneralPath)shape).lineTo(47.34996, 61.63121);
((GeneralPath)shape).curveTo(51.258373, 61.07848, 54.13121, 59.574383, 54.13121, 57.72496);
((GeneralPath)shape).curveTo(54.13121, 57.625732, 54.022198, 57.54122, 54.00621, 57.44371);
((GeneralPath)shape).lineTo(54.00621, 6.5062113);
((GeneralPath)shape).lineTo(34.50621, 6.5062113);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.0124228f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(34.50621, 6.5062113);
((GeneralPath)shape).lineTo(34.50621, 57.50621);
((GeneralPath)shape).lineTo(34.59996, 57.50621);
((GeneralPath)shape).curveTo(34.590523, 57.581318, 34.50621, 57.64886, 34.50621, 57.72496);
((GeneralPath)shape).curveTo(34.50621, 59.678455, 37.72298, 61.233765, 41.97496, 61.69371);
((GeneralPath)shape).lineTo(41.97496, 71.81871);
((GeneralPath)shape).lineTo(47.59996, 67.81871);
((GeneralPath)shape).lineTo(47.34996, 61.63121);
((GeneralPath)shape).curveTo(51.258373, 61.07848, 54.13121, 59.574383, 54.13121, 57.72496);
((GeneralPath)shape).curveTo(54.13121, 57.625732, 54.022198, 57.54122, 54.00621, 57.44371);
((GeneralPath)shape).lineTo(54.00621, 6.5062113);
((GeneralPath)shape).lineTo(34.50621, 6.5062113);
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -2.318181037902832f, -7.681818008422852f));
// _0_0_2_1 is ShapeNode
paint = getColor(0, 155, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(56.545452, 14.0);
((GeneralPath)shape).curveTo(56.545452, 16.309555, 52.149704, 18.181818, 46.727272, 18.181818);
((GeneralPath)shape).curveTo(41.304836, 18.181818, 36.90909, 16.309555, 36.90909, 14.0);
((GeneralPath)shape).curveTo(36.90909, 11.690446, 41.304836, 9.818182, 46.727272, 9.818182);
((GeneralPath)shape).curveTo(52.149704, 9.818182, 56.545452, 11.690446, 56.545452, 14.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(56.545452, 14.0);
((GeneralPath)shape).curveTo(56.545452, 16.309555, 52.149704, 18.181818, 46.727272, 18.181818);
((GeneralPath)shape).curveTo(41.304836, 18.181818, 36.90909, 16.309555, 36.90909, 14.0);
((GeneralPath)shape).curveTo(36.90909, 11.690446, 41.304836, 9.818182, 46.727272, 9.818182);
((GeneralPath)shape).curveTo(52.149704, 9.818182, 56.545452, 11.690446, 56.545452, 14.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2_1;
g.setTransform(defaultTransform__0_0_2_1);
g.setClip(clip__0_0_2_1);
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
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(33.454544, 46.0);
((GeneralPath)shape).lineTo(33.454544, 46.0);
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
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(0.80058205f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(21.322016, 45.39059);
((GeneralPath)shape).curveTo(26.13253, 49.35462, 26.13253, 48.482536, 26.13253, 48.482536);
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
// _0_0_5 is TextNode of 'X'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.338417, 30.99925);
((GeneralPath)shape).lineTo(26.859251, 30.99925);
((GeneralPath)shape).lineTo(19.150917, 23.0305);
((GeneralPath)shape).lineTo(11.473834, 30.99925);
((GeneralPath)shape).lineTo(6.0050836, 30.99925);
((GeneralPath)shape).lineTo(16.619667, 20.093);
((GeneralPath)shape).lineTo(6.0050836, 9.113833);
((GeneralPath)shape).lineTo(11.275917, 9.113833);
((GeneralPath)shape).lineTo(19.150917, 17.176332);
((GeneralPath)shape).lineTo(27.025917, 9.113833);
((GeneralPath)shape).lineTo(32.338417, 9.113833);
((GeneralPath)shape).lineTo(21.682167, 20.093);
((GeneralPath)shape).lineTo(32.338417, 30.99925);
((GeneralPath)shape).closePath();
paint = getColor(255, 0, 0, 255, disabled);
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
	public RemoveAnnotationIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public RemoveAnnotationIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public RemoveAnnotationIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public RemoveAnnotationIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public RemoveAnnotationIcon(int width, int height) {
		this(width, height, false);
	}
	
	public RemoveAnnotationIcon(int width, int height, boolean disabled) {
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

