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
public class RemotePluginIcon implements
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
// _0_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(1.6422368288040161, 117.82710266113281), new Point2D.Double(15.343062400817871, 117.82710266113281), new float[] {0.0f,0.23762377f,0.7810999f,1.0f}, new Color[] {new Color(104, 104, 104, 0),new Color(104, 104, 104, 255),new Color(104, 104, 104, 255),new Color(104, 104, 104, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-3.0401434898376465f, 0.0f, 0.0f, 0.4048938751220703f, 50.209373474121094f, -8.419529914855957f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(44.662003, 37.068935);
((GeneralPath)shape).lineTo(4.1190033, 37.068935);
((GeneralPath)shape).lineTo(4.1190033, 41.506836);
((GeneralPath)shape).lineTo(44.662003, 41.506836);
((GeneralPath)shape).lineTo(44.662003, 37.068935);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0;
g.setTransform(defaultTransform__0_0);
g.setClip(clip__0_0);
float alpha__0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1 = g.getClip();
AffineTransform defaultTransform__0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(0.6121002435684204, 372.57818603515625), new Point2D.Double(5.08563756942749, 372.57818603515625), new float[] {0.0f,0.1f,0.9f,1.0f}, new Color[] {new Color(71, 71, 71, 0),new Color(71, 71, 71, 255),new Color(71, 71, 71, 255),new Color(71, 71, 71, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-9.062850952148438f, 0.0f, 0.0f, 0.24542687833309174f, 50.209373474121094f, -50.49237060546875f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(44.662003, 40.399555);
((GeneralPath)shape).lineTo(4.1190033, 40.399555);
((GeneralPath)shape).lineTo(4.1190033, 41.497482);
((GeneralPath)shape).lineTo(44.662003, 41.497482);
((GeneralPath)shape).lineTo(44.662003, 40.399555);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1;
g.setTransform(defaultTransform__0_1);
g.setClip(clip__0_1);
float alpha__0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_2 = g.getClip();
AffineTransform defaultTransform__0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(0.6121002435684204, 372.57818603515625), new Point2D.Double(5.08563756942749, 372.57818603515625), new float[] {0.0f,0.1f,0.9f,1.0f}, new Color[] {new Color(71, 71, 71, 0),new Color(71, 71, 71, 255),new Color(71, 71, 71, 255),new Color(71, 71, 71, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-9.062850952148438f, 0.0f, 0.0f, 0.25364917516708374f, 50.209373474121094f, -48.951820373535156f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(44.662003, 35.99865);
((GeneralPath)shape).lineTo(4.1190033, 35.99865);
((GeneralPath)shape).lineTo(4.1190033, 37.13336);
((GeneralPath)shape).lineTo(44.662003, 37.13336);
((GeneralPath)shape).lineTo(44.662003, 35.99865);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_2;
g.setTransform(defaultTransform__0_2);
g.setClip(clip__0_2);
float alpha__0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_3 = g.getClip();
AffineTransform defaultTransform__0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_3 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(1.6422368288040161, 117.82710266113281), new Point2D.Double(15.343062400817871, 117.82710266113281), new float[] {0.0f,0.10827128f,0.920539f,1.0f}, new Color[] {new Color(255, 255, 255, 0),new Color(255, 255, 255, 180),new Color(255, 255, 255, 180),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-3.0401434898376465f, 0.0f, 0.0f, 0.20244693756103516f, 50.209373474121094f, 14.344339370727539f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(44.662003, 37.088634);
((GeneralPath)shape).lineTo(4.1190033, 37.088634);
((GeneralPath)shape).lineTo(4.1190033, 39.307583);
((GeneralPath)shape).lineTo(44.662003, 39.307583);
((GeneralPath)shape).lineTo(44.662003, 37.088634);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_3;
g.setTransform(defaultTransform__0_3);
g.setClip(clip__0_3);
float alpha__0_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_4 = g.getClip();
AffineTransform defaultTransform__0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_4 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(23.100046157836914, 38.29674530029297), new Point2D.Double(23.100046157836914, 43.91546630859375), new float[] {0.0f,0.1980198f,0.5990099f,1.0f}, new Color[] {new Color(122, 122, 122, 255),new Color(197, 197, 197, 255),new Color(98, 98, 98, 255),new Color(136, 136, 136, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0915021896362305f, 0.0f, 0.0f, 1.1277432441711426f, -1.4283732175827026f, -7.310055732727051f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(22.387596, 25.38478);
((GeneralPath)shape).curveTo(21.518353, 25.38478, 20.827398, 26.075733, 20.827398, 26.944979);
((GeneralPath)shape).lineTo(20.827398, 35.370052);
((GeneralPath)shape).lineTo(17.429632, 35.370052);
((GeneralPath)shape).curveTo(16.560387, 35.370052, 15.869433, 36.061005, 15.869433, 36.930252);
((GeneralPath)shape).lineTo(15.869433, 41.05611);
((GeneralPath)shape).curveTo(15.869433, 41.925354, 16.560387, 42.616314, 17.429632, 42.61631);
((GeneralPath)shape).lineTo(30.951355, 42.61631);
((GeneralPath)shape).curveTo(31.820599, 42.61631, 32.511555, 41.925354, 32.511555, 41.05611);
((GeneralPath)shape).lineTo(32.511555, 36.930252);
((GeneralPath)shape).curveTo(32.511555, 36.06101, 31.8206, 35.370052, 30.951357, 35.370052);
((GeneralPath)shape).lineTo(28.073656, 35.370052);
((GeneralPath)shape).lineTo(28.073656, 26.94498);
((GeneralPath)shape).curveTo(28.073656, 26.075737, 27.382704, 25.384783, 26.513456, 25.384783);
((GeneralPath)shape).lineTo(22.387598, 25.384783);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(67, 67, 67, 255);
stroke = new BasicStroke(1.1094747f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(22.387596, 25.38478);
((GeneralPath)shape).curveTo(21.518353, 25.38478, 20.827398, 26.075733, 20.827398, 26.944979);
((GeneralPath)shape).lineTo(20.827398, 35.370052);
((GeneralPath)shape).lineTo(17.429632, 35.370052);
((GeneralPath)shape).curveTo(16.560387, 35.370052, 15.869433, 36.061005, 15.869433, 36.930252);
((GeneralPath)shape).lineTo(15.869433, 41.05611);
((GeneralPath)shape).curveTo(15.869433, 41.925354, 16.560387, 42.616314, 17.429632, 42.61631);
((GeneralPath)shape).lineTo(30.951355, 42.61631);
((GeneralPath)shape).curveTo(31.820599, 42.61631, 32.511555, 41.925354, 32.511555, 41.05611);
((GeneralPath)shape).lineTo(32.511555, 36.930252);
((GeneralPath)shape).curveTo(32.511555, 36.06101, 31.8206, 35.370052, 30.951357, 35.370052);
((GeneralPath)shape).lineTo(28.073656, 35.370052);
((GeneralPath)shape).lineTo(28.073656, 26.94498);
((GeneralPath)shape).curveTo(28.073656, 26.075737, 27.382704, 25.384783, 26.513456, 25.384783);
((GeneralPath)shape).lineTo(22.387598, 25.384783);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_4;
g.setTransform(defaultTransform__0_4);
g.setClip(clip__0_4);
float alpha__0_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_5 = g.getClip();
AffineTransform defaultTransform__0_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_5 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(36.37306594848633, 22.227985382080078), new Point2D.Double(38.10511779785156, 22.227985382080078), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 115),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-1.2633486986160278f, 0.0f, 0.0f, 1.9216666221618652f, 72.8623275756836f, -8.974266052246094f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.578056, 32.0763);
((GeneralPath)shape).lineTo(25.05457, 32.0763);
((GeneralPath)shape).curveTo(24.87045, 32.0763, 24.72222, 32.22453, 24.72222, 32.40865);
((GeneralPath)shape).lineTo(24.72222, 35.072376);
((GeneralPath)shape).curveTo(24.72222, 35.256496, 24.870447, 35.404724, 25.05457, 35.404724);
((GeneralPath)shape).lineTo(26.578056, 35.404724);
((GeneralPath)shape).curveTo(26.762177, 35.404724, 26.910406, 35.256496, 26.910406, 35.072376);
((GeneralPath)shape).lineTo(26.910406, 32.40865);
((GeneralPath)shape).curveTo(26.910406, 32.22453, 26.762178, 32.0763, 26.578056, 32.0763);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_5;
g.setTransform(defaultTransform__0_5);
g.setClip(clip__0_5);
float alpha__0_6 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_6 = g.getClip();
AffineTransform defaultTransform__0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_6 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.247644424438477, 15.716078758239746), 29.99335f, new Point2D.Double(18.247644424438477, 15.716078758239746), new float[] {0.0f,0.15517241f,0.75f,1.0f}, new Color[] {new Color(211, 233, 255, 255),new Color(211, 233, 255, 255),new Color(64, 116, 174, 255),new Color(54, 72, 108, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.7488587498664856f, 0.0f, 0.0f, 0.748874306678772f, 42.85432052612305f, 0.0209099892526865f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(9.93426, 17.608412);
((GeneralPath)shape).curveTo(9.93426, 25.628561, 16.435997, 32.130222, 24.455044, 32.130222);
((GeneralPath)shape).curveTo(32.474823, 32.130222, 38.976192, 25.628487, 38.976192, 17.608412);
((GeneralPath)shape).curveTo(38.976192, 9.588633, 32.474823, 3.087634, 24.455044, 3.087634);
((GeneralPath)shape).curveTo(16.435997, 3.087634, 9.93426, 9.588633, 9.93426, 17.608412);
((GeneralPath)shape).lineTo(9.93426, 17.608412);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(57, 57, 108, 255);
stroke = new BasicStroke(2.2189493f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(9.93426, 17.608412);
((GeneralPath)shape).curveTo(9.93426, 25.628561, 16.435997, 32.130222, 24.455044, 32.130222);
((GeneralPath)shape).curveTo(32.474823, 32.130222, 38.976192, 25.628487, 38.976192, 17.608412);
((GeneralPath)shape).curveTo(38.976192, 9.588633, 32.474823, 3.087634, 24.455044, 3.087634);
((GeneralPath)shape).curveTo(16.435997, 3.087634, 9.93426, 9.588633, 9.93426, 17.608412);
((GeneralPath)shape).lineTo(9.93426, 17.608412);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_6;
g.setTransform(defaultTransform__0_6);
g.setClip(clip__0_6);
float alpha__0_7 = origAlpha;
origAlpha = origAlpha * 0.42159382f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_7 = g.getClip();
AffineTransform defaultTransform__0_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_7 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(11.82690715789795, 10.476452827453613), 32.66485f, new Point2D.Double(11.82690715789795, 10.476452827453613), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.7383960485458374f, 0.0f, 0.0f, 0.6348823308944702f, 13.460090637207031f, 0.020913319662213326f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.68387, 11.456896);
((GeneralPath)shape).curveTo(32.68507, 15.301288, 29.06084, 18.41835, 24.589645, 18.41835);
((GeneralPath)shape).curveTo(20.11845, 18.41835, 16.494179, 15.301287, 16.495422, 11.456896);
((GeneralPath)shape).curveTo(16.494223, 7.612504, 20.11845, 4.4954424, 24.589645, 4.4954424);
((GeneralPath)shape).curveTo(29.06084, 4.4954424, 32.685112, 7.6125045, 32.68387, 11.456896);
((GeneralPath)shape).lineTo(32.68387, 11.456896);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_7;
g.setTransform(defaultTransform__0_7);
g.setClip(clip__0_7);
float alpha__0_8 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_8 = g.getClip();
AffineTransform defaultTransform__0_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_8 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(38.568512, 15.43419);
((GeneralPath)shape).curveTo(38.568512, 15.62745, 38.568512, 15.43419, 38.568512, 15.43419);
((GeneralPath)shape).lineTo(38.16765, 15.888243);
((GeneralPath)shape).curveTo(37.921936, 15.598684, 37.646065, 15.35518, 37.36592, 15.10086);
((GeneralPath)shape).lineTo(36.750977, 15.19135);
((GeneralPath)shape).lineTo(36.189148, 14.55625);
((GeneralPath)shape).lineTo(36.189148, 15.342234);
((GeneralPath)shape).lineTo(36.670494, 15.706462);
((GeneralPath)shape).lineTo(36.990875, 16.06929);
((GeneralPath)shape).lineTo(37.419033, 15.585076);
((GeneralPath)shape).curveTo(37.526806, 15.7869425, 37.63311, 15.98881, 37.74015, 16.190676);
((GeneralPath)shape).lineTo(37.74015, 16.795612);
((GeneralPath)shape).lineTo(37.258068, 17.34015);
((GeneralPath)shape).lineTo(36.37586, 17.945751);
((GeneralPath)shape).lineTo(35.707726, 18.612486);
((GeneralPath)shape).lineTo(35.279568, 18.1268);
((GeneralPath)shape).lineTo(35.493645, 17.58226);
((GeneralPath)shape).lineTo(35.06615, 17.098045);
((GeneralPath)shape).lineTo(34.34417, 15.55484);
((GeneralPath)shape).lineTo(33.72923, 14.859416);
((GeneralPath)shape).lineTo(33.568264, 15.040463);
((GeneralPath)shape).lineTo(33.809635, 15.9184065);
((GeneralPath)shape).lineTo(34.263687, 16.432783);
((GeneralPath)shape).curveTo(34.52301, 17.181395, 34.779533, 17.896904, 35.120075, 18.612486);
((GeneralPath)shape).curveTo(35.648136, 18.612486, 36.14596, 18.556425, 36.670414, 18.490364);
((GeneralPath)shape).lineTo(36.670414, 18.914253);
((GeneralPath)shape).lineTo(36.028843, 20.487988);
((GeneralPath)shape).lineTo(35.440456, 21.15325);
((GeneralPath)shape).lineTo(34.95911, 22.183475);
((GeneralPath)shape).curveTo(34.95911, 22.74817, 34.95911, 23.312868, 34.95911, 23.877491);
((GeneralPath)shape).lineTo(35.12007, 24.544224);
((GeneralPath)shape).lineTo(34.852806, 24.845993);
((GeneralPath)shape).lineTo(34.263687, 25.209484);
((GeneralPath)shape).lineTo(33.648746, 25.723862);
((GeneralPath)shape).lineTo(34.157383, 26.298637);
((GeneralPath)shape).lineTo(33.46196, 26.904972);
((GeneralPath)shape).lineTo(33.59556, 27.29723);
((GeneralPath)shape).lineTo(32.552387, 28.47834);
((GeneralPath)shape).lineTo(31.857698, 28.47834);
((GeneralPath)shape).lineTo(31.269314, 28.841831);
((GeneralPath)shape).lineTo(30.894272, 28.841831);
((GeneralPath)shape).lineTo(30.894272, 28.357616);
((GeneralPath)shape).lineTo(30.73478, 27.387716);
((GeneralPath)shape).curveTo(30.527838, 26.779911, 30.31236, 26.176445, 30.093204, 25.572979);
((GeneralPath)shape).curveTo(30.093204, 25.127533, 30.119764, 24.686428, 30.146395, 24.241058);
((GeneralPath)shape).lineTo(30.414396, 23.63612);
((GeneralPath)shape).lineTo(30.039354, 22.909061);
((GeneralPath)shape).lineTo(30.066645, 21.910471);
((GeneralPath)shape).lineTo(29.558006, 21.335695);
((GeneralPath)shape).lineTo(29.812326, 20.503733);
((GeneralPath)shape).lineTo(29.398516, 20.034231);
((GeneralPath)shape).lineTo(28.675798, 20.034231);
((GeneralPath)shape).lineTo(28.435162, 19.761961);
((GeneralPath)shape).lineTo(27.71318, 20.216381);
((GeneralPath)shape).lineTo(27.419355, 19.882683);
((GeneralPath)shape).lineTo(26.750488, 20.457752);
((GeneralPath)shape).curveTo(26.296438, 19.943005, 25.84165, 19.428629, 25.386936, 18.914253);
((GeneralPath)shape).lineTo(24.852402, 17.642656);
((GeneralPath)shape).lineTo(25.333748, 16.91707);
((GeneralPath)shape).lineTo(25.06648, 16.61464);
((GeneralPath)shape).lineTo(25.65413, 15.221584);
((GeneralPath)shape).curveTo(26.136948, 14.620988, 26.641247, 14.044814, 27.151356, 13.466508);
((GeneralPath)shape).lineTo(28.060858, 13.2244005);
((GeneralPath)shape).lineTo(29.076738, 13.103678);
((GeneralPath)shape).lineTo(29.772161, 13.28546);
((GeneralPath)shape).lineTo(30.76141, 14.283317);
((GeneralPath)shape).lineTo(31.10916, 13.890325);
((GeneralPath)shape).lineTo(31.58977, 13.830005);
((GeneralPath)shape).lineTo(32.49927, 14.132438);
((GeneralPath)shape).lineTo(33.194695, 14.132438);
((GeneralPath)shape).lineTo(33.67604, 13.708548);
((GeneralPath)shape).lineTo(33.890118, 13.406116);
((GeneralPath)shape).lineTo(33.408035, 13.1036825);
((GeneralPath)shape).lineTo(32.60557, 13.043363);
((GeneralPath)shape).curveTo(32.382885, 12.734451, 32.175945, 12.40973, 31.911545, 12.135254);
((GeneralPath)shape).lineTo(31.643541, 12.255976);
((GeneralPath)shape).lineTo(31.536499, 13.043358);
((GeneralPath)shape).lineTo(31.055155, 12.498819);
((GeneralPath)shape).lineTo(30.948856, 11.892485);
((GeneralPath)shape).lineTo(30.414322, 11.470066);
((GeneralPath)shape).lineTo(30.199509, 11.470066);
((GeneralPath)shape).lineTo(30.734705, 12.075004);
((GeneralPath)shape).lineTo(30.520626, 12.619543);
((GeneralPath)shape).lineTo(30.093132, 12.740265);
((GeneralPath)shape).lineTo(30.3604, 12.195726);
((GeneralPath)shape).lineTo(29.878319, 11.954354);
((GeneralPath)shape).lineTo(29.45156, 11.4701395);
((GeneralPath)shape).lineTo(28.64836, 11.651188);
((GeneralPath)shape).lineTo(28.54206, 11.892559);
((GeneralPath)shape).lineTo(28.060717, 12.195727);
((GeneralPath)shape).lineTo(27.79345, 12.861725);
((GeneralPath)shape).lineTo(27.125319, 13.194319);
((GeneralPath)shape).lineTo(26.83076, 12.861725);
((GeneralPath)shape).lineTo(26.510378, 12.861725);
((GeneralPath)shape).lineTo(26.510378, 11.771912);
((GeneralPath)shape).lineTo(27.205801, 11.408419);
((GeneralPath)shape).lineTo(27.740335, 11.408419);
((GeneralPath)shape).lineTo(27.63256, 10.985265);
((GeneralPath)shape).lineTo(27.205801, 10.561375);
((GeneralPath)shape).lineTo(27.92712, 10.409754);
((GeneralPath)shape).lineTo(28.327984, 9.956437);
((GeneralPath)shape).lineTo(28.648365, 9.411162);
((GeneralPath)shape).lineTo(29.237486, 9.411162);
((GeneralPath)shape).lineTo(29.076523, 8.988008);
((GeneralPath)shape).lineTo(29.451565, 8.7459);
((GeneralPath)shape).lineTo(29.451565, 9.230115);
((GeneralPath)shape).lineTo(30.253294, 9.411162);
((GeneralPath)shape).lineTo(31.055021, 8.7459);
((GeneralPath)shape).lineTo(31.108871, 8.442733);
((GeneralPath)shape).lineTo(31.80356, 7.9588866);
((GeneralPath)shape).curveTo(31.552109, 7.9901514, 31.30066, 8.013106, 31.054947, 8.079977);
((GeneralPath)shape).lineTo(31.054947, 7.5347757);
((GeneralPath)shape).lineTo(31.322214, 6.9294705);
((GeneralPath)shape).lineTo(31.054947, 6.9294705);
((GeneralPath)shape).lineTo(30.467592, 7.4740095);
((GeneralPath)shape).lineTo(30.30663, 7.7768097);
((GeneralPath)shape).lineTo(30.467592, 8.201067);
((GeneralPath)shape).lineTo(30.1996, 8.926646);
((GeneralPath)shape).lineTo(29.772104, 8.684539);
((GeneralPath)shape).lineTo(29.398535, 8.261384);
((GeneralPath)shape).lineTo(28.809414, 8.684539);
((GeneralPath)shape).lineTo(28.595335, 7.716478);
((GeneralPath)shape).lineTo(29.611216, 7.050848);
((GeneralPath)shape).lineTo(29.611216, 6.687356);
((GeneralPath)shape).lineTo(30.25345, 6.2638335);
((GeneralPath)shape).lineTo(31.26933, 6.0213585);
((GeneralPath)shape).lineTo(31.964752, 6.2638335);
((GeneralPath)shape).lineTo(33.247826, 6.505941);
((GeneralPath)shape).lineTo(32.927444, 6.8687706);
((GeneralPath)shape).lineTo(32.23202, 6.8687706);
((GeneralPath)shape).lineTo(32.927444, 7.5950923);
((GeneralPath)shape).lineTo(33.46198, 6.990155);
((GeneralPath)shape).lineTo(33.62434, 6.723992);
((GeneralPath)shape).curveTo(33.62434, 6.723992, 35.67471, 8.561682, 36.846478, 10.571887);
((GeneralPath)shape).curveTo(38.018246, 12.582753, 38.56852, 14.952845, 38.56852, 15.43419);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_8;
g.setTransform(defaultTransform__0_8);
g.setClip(clip__0_8);
float alpha__0_9 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_9 = g.getClip();
AffineTransform defaultTransform__0_9 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_9 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(25.32581, 6.990155);
((GeneralPath)shape).lineTo(25.27196, 7.352985);
((GeneralPath)shape).lineTo(25.647003, 7.5950923);
((GeneralPath)shape).lineTo(26.28784, 7.1715703);
((GeneralPath)shape).lineTo(25.967459, 6.8083725);
((GeneralPath)shape).lineTo(25.5393, 7.0508475);
((GeneralPath)shape).lineTo(25.325958, 6.9901547);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_9;
g.setTransform(defaultTransform__0_9);
g.setClip(clip__0_9);
float alpha__0_10 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_10 = g.getClip();
AffineTransform defaultTransform__0_10 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_10 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(25.914194, 4.5087576);
((GeneralPath)shape).lineTo(24.523346, 3.9638507);
((GeneralPath)shape).lineTo(22.919817, 4.1452656);
((GeneralPath)shape).lineTo(20.940584, 4.6898046);
((GeneralPath)shape).lineTo(20.566278, 5.0532966);
((GeneralPath)shape).lineTo(21.796236, 5.900341);
((GeneralPath)shape).lineTo(21.796236, 6.384556);
((GeneralPath)shape).lineTo(21.314892, 6.86877);
((GeneralPath)shape).lineTo(21.957127, 8.140735);
((GeneralPath)shape).lineTo(22.383886, 7.897892);
((GeneralPath)shape).lineTo(22.919819, 7.050847);
((GeneralPath)shape).curveTo(23.74597, 6.795425, 24.48671, 6.505941, 25.271957, 6.142743);
((GeneralPath)shape).lineTo(25.914194, 4.508684);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_10;
g.setTransform(defaultTransform__0_10);
g.setClip(clip__0_10);
float alpha__0_11 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_11 = g.getClip();
AffineTransform defaultTransform__0_11 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_11 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(27.35823, 9.593379);
((GeneralPath)shape).lineTo(27.144154, 9.048104);
((GeneralPath)shape).lineTo(26.76911, 9.169489);
((GeneralPath)shape).lineTo(26.876884, 9.834751);
((GeneralPath)shape).lineTo(27.358229, 9.593379);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_11;
g.setTransform(defaultTransform__0_11);
g.setClip(clip__0_11);
float alpha__0_12 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_12 = g.getClip();
AffineTransform defaultTransform__0_12 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_12 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(27.571573, 9.471258);
((GeneralPath)shape).lineTo(27.46453, 10.198316);
((GeneralPath)shape).lineTo(28.052916, 10.076931);
((GeneralPath)shape).lineTo(28.48041, 9.653777);
((GeneralPath)shape).lineTo(28.106106, 9.290285);
((GeneralPath)shape).curveTo(27.98038, 8.955483, 27.835968, 8.642973, 27.67861, 8.321856);
((GeneralPath)shape).lineTo(27.358227, 8.321856);
((GeneralPath)shape).lineTo(27.358227, 8.684686);
((GeneralPath)shape).lineTo(27.57157, 8.926793);
((GeneralPath)shape).lineTo(27.57157, 9.471333);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_12;
g.setTransform(defaultTransform__0_12);
g.setClip(clip__0_12);
float alpha__0_13 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_13 = g.getClip();
AffineTransform defaultTransform__0_13 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_13 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(19.657438, 20.972128);
((GeneralPath)shape).lineTo(19.22928, 20.12442);
((GeneralPath)shape).lineTo(18.427185, 19.943005);
((GeneralPath)shape).lineTo(17.999395, 18.79353);
((GeneralPath)shape).lineTo(16.930033, 18.914253);
((GeneralPath)shape).lineTo(16.021193, 18.248991);
((GeneralPath)shape).lineTo(15.058132, 19.096037);
((GeneralPath)shape).lineTo(15.058132, 19.229633);
((GeneralPath)shape).curveTo(14.76681, 19.145544, 14.408689, 19.134073, 14.149293, 18.974579);
((GeneralPath)shape).lineTo(13.935214, 18.369642);
((GeneralPath)shape).lineTo(13.935214, 17.703644);
((GeneralPath)shape).lineTo(13.29364, 17.763964);
((GeneralPath)shape).curveTo(13.34719, 17.340075, 13.400316, 16.916918, 13.454236, 16.493103);
((GeneralPath)shape).lineTo(13.079562, 16.493103);
((GeneralPath)shape).lineTo(12.705624, 16.977318);
((GeneralPath)shape).lineTo(12.33095, 17.158365);
((GeneralPath)shape).lineTo(11.796048, 16.856596);
((GeneralPath)shape).lineTo(11.742498, 16.190598);
((GeneralPath)shape).lineTo(11.849541, 15.464276);
((GeneralPath)shape).lineTo(12.652003, 14.859339);
((GeneralPath)shape).lineTo(13.293577, 14.859339);
((GeneralPath)shape).lineTo(13.400253, 14.495848);
((GeneralPath)shape).lineTo(14.202347, 14.676893);
((GeneralPath)shape).lineTo(14.790732, 15.403952);
((GeneralPath)shape).lineTo(14.897775, 14.192679);
((GeneralPath)shape).lineTo(15.913949, 13.345635);
((GeneralPath)shape).lineTo(16.288256, 12.4375305);
((GeneralPath)shape).lineTo(17.036867, 12.135098);
((GeneralPath)shape).lineTo(17.464657, 11.530161);
((GeneralPath)shape).lineTo(18.426981, 11.347717);
((GeneralPath)shape).lineTo(18.908693, 10.622131);
((GeneralPath)shape).curveTo(18.42735, 10.622131, 17.946003, 10.622131, 17.464657, 10.622131);
((GeneralPath)shape).lineTo(18.373793, 10.19824);
((GeneralPath)shape).lineTo(19.015, 10.19824);
((GeneralPath)shape).lineTo(19.924496, 9.895073);
((GeneralPath)shape).lineTo(20.031538, 9.532978);
((GeneralPath)shape).lineTo(19.71042, 9.229811);
((GeneralPath)shape).lineTo(19.336115, 9.108427);
((GeneralPath)shape).lineTo(19.443155, 8.745596);
((GeneralPath)shape).lineTo(19.175888, 8.201057);
((GeneralPath)shape).lineTo(18.533949, 8.44243);
((GeneralPath)shape).lineTo(18.64099, 7.958583);
((GeneralPath)shape).lineTo(17.892378, 7.534693);
((GeneralPath)shape).lineTo(17.304361, 8.563078);
((GeneralPath)shape).lineTo(17.35755, 8.926571);
((GeneralPath)shape).lineTo(16.769531, 9.169414);
((GeneralPath)shape).lineTo(16.394857, 9.956059);
((GeneralPath)shape).lineTo(16.23463, 9.229738);
((GeneralPath)shape).lineTo(15.218456, 8.805847);
((GeneralPath)shape).lineTo(15.05786, 8.261309);
((GeneralPath)shape).lineTo(16.394857, 7.4739265);
((GeneralPath)shape).lineTo(16.983242, 6.929387);
((GeneralPath)shape).lineTo(17.036797, 6.2637577);
((GeneralPath)shape).lineTo(16.716047, 6.0819745);
((GeneralPath)shape).lineTo(16.288258, 6.021282);
((GeneralPath)shape).lineTo(16.02099, 6.687279);
((GeneralPath)shape).curveTo(16.02099, 6.687279, 15.573779, 6.774896, 15.458795, 6.8032937);
((GeneralPath)shape).curveTo(13.9903345, 8.156476, 11.023251, 11.077581, 10.333934, 16.592197);
((GeneralPath)shape).curveTo(10.361224, 16.720057, 10.833597, 17.46146, 10.833597, 17.46146);
((GeneralPath)shape).lineTo(11.956516, 18.12672);
((GeneralPath)shape).lineTo(13.079435, 18.429888);
((GeneralPath)shape).lineTo(13.561149, 19.035488);
((GeneralPath)shape).lineTo(14.309394, 19.580027);
((GeneralPath)shape).lineTo(14.737183, 19.519707);
((GeneralPath)shape).lineTo(15.057932, 19.664118);
((GeneralPath)shape).lineTo(15.057932, 19.761818);
((GeneralPath)shape).lineTo(14.630438, 20.911661);
((GeneralPath)shape).lineTo(14.3093195, 21.395876);
((GeneralPath)shape).lineTo(14.416362, 21.638718);
((GeneralPath)shape).lineTo(14.149095, 22.545351);
((GeneralPath)shape).lineTo(15.111786, 24.301163);
((GeneralPath)shape).lineTo(16.074108, 25.14887);
((GeneralPath)shape).lineTo(16.502266, 25.753807);
((GeneralPath)shape).lineTo(16.448416, 27.025404);
((GeneralPath)shape).lineTo(16.769533, 27.75099);
((GeneralPath)shape).lineTo(16.448416, 29.143309);
((GeneralPath)shape).curveTo(16.448416, 29.143309, 16.423256, 29.13461, 16.464235, 29.274036);
((GeneralPath)shape).curveTo(16.505575, 29.413445, 18.177671, 30.341633, 18.283976, 30.262623);
((GeneralPath)shape).curveTo(18.389908, 30.182142, 18.48047, 30.111738, 18.48047, 30.111738);
((GeneralPath)shape).lineTo(18.373795, 29.809969);
((GeneralPath)shape).lineTo(18.801289, 29.38608);
((GeneralPath)shape).lineTo(18.961885, 28.962189);
((GeneralPath)shape).lineTo(19.657309, 28.719345);
((GeneralPath)shape).lineTo(20.191843, 27.387424);
((GeneralPath)shape).lineTo(20.031614, 27.02533);
((GeneralPath)shape).lineTo(20.405184, 26.480791);
((GeneralPath)shape).lineTo(21.207647, 26.298346);
((GeneralPath)shape).lineTo(21.635803, 25.329916);
((GeneralPath)shape).lineTo(21.52876, 24.120115);
((GeneralPath)shape).lineTo(22.170336, 23.21201);
((GeneralPath)shape).lineTo(22.277378, 22.303904);
((GeneralPath)shape).curveTo(21.399435, 21.868538, 20.528704, 21.420223, 19.657307, 20.971983);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_13;
g.setTransform(defaultTransform__0_13);
g.setClip(clip__0_13);
float alpha__0_14 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_14 = g.getClip();
AffineTransform defaultTransform__0_14 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_14 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.480667, 7.231895);
((GeneralPath)shape).lineTo(19.015202, 7.595093);
((GeneralPath)shape).lineTo(19.443357, 7.595093);
((GeneralPath)shape).lineTo(19.443357, 7.171571);
((GeneralPath)shape).lineTo(18.908823, 6.9294634);
((GeneralPath)shape).lineTo(18.480665, 7.231895);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_14;
g.setTransform(defaultTransform__0_14);
g.setClip(clip__0_14);
float alpha__0_15 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_15 = g.getClip();
AffineTransform defaultTransform__0_15 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_15 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.090555, 6.748048);
((GeneralPath)shape).lineTo(16.82292, 7.4136777);
((GeneralPath)shape).lineTo(17.357822, 7.4136777);
((GeneralPath)shape).lineTo(17.625458, 6.8083725);
((GeneralPath)shape).curveTo(17.856089, 6.6452756, 18.085615, 6.4811487, 18.320515, 6.3241577);
((GeneralPath)shape).lineTo(18.855417, 6.5059404);
((GeneralPath)shape).curveTo(19.211773, 6.748048, 19.568129, 6.9901547, 19.92478, 7.2318945);
((GeneralPath)shape).lineTo(20.459976, 6.748048);
((GeneralPath)shape).lineTo(19.871223, 6.5059404);
((GeneralPath)shape).lineTo(19.603588, 5.9610333);
((GeneralPath)shape).lineTo(18.58771, 5.839943);
((GeneralPath)shape).lineTo(18.53415, 5.537143);
((GeneralPath)shape).lineTo(18.052805, 5.658528);
((GeneralPath)shape).lineTo(17.839169, 6.0821238);
((GeneralPath)shape).lineTo(17.571533, 5.5372167);
((GeneralPath)shape).lineTo(17.464857, 5.779324);
((GeneralPath)shape).lineTo(17.518408, 6.3846292);
((GeneralPath)shape).lineTo(17.090546, 6.748048);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_15;
g.setTransform(defaultTransform__0_15);
g.setClip(clip__0_15);
float alpha__0_16 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_16 = g.getClip();
AffineTransform defaultTransform__0_16 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_16 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(19.015203, 5.234344);
((GeneralPath)shape).lineTo(19.282837, 4.9926047);
((GeneralPath)shape).lineTo(19.81774, 4.871514);
((GeneralPath)shape).curveTo(20.1841, 4.693336, 20.551933, 4.573349, 20.940659, 4.4476237);
((GeneralPath)shape).lineTo(20.727316, 4.0844264);
((GeneralPath)shape).lineTo(20.036894, 4.1835938);
((GeneralPath)shape).lineTo(19.7107, 4.508684);
((GeneralPath)shape).lineTo(19.17293, 4.5866647);
((GeneralPath)shape).lineTo(18.69482, 4.8111897);
((GeneralPath)shape).lineTo(18.462423, 4.923599);
((GeneralPath)shape).lineTo(18.320513, 5.113621);
((GeneralPath)shape).lineTo(19.015202, 5.234344);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_16;
g.setTransform(defaultTransform__0_16);
g.setClip(clip__0_16);
float alpha__0_17 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_17 = g.getClip();
AffineTransform defaultTransform__0_17 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_17 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(19.924706, 10.984962);
((GeneralPath)shape).lineTo(20.245823, 10.50075);
((GeneralPath)shape).lineTo(19.76411, 10.137918);
((GeneralPath)shape).lineTo(19.924707, 10.984962);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_17;
g.setTransform(defaultTransform__0_17);
g.setClip(clip__0_17);
float alpha__0_18 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_18 = g.getClip();
AffineTransform defaultTransform__0_18 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_18 is ShapeNode
paint = new Color(255, 255, 255, 245);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(28.605383, 37.016926);
((GeneralPath)shape).lineTo(18.620111, 37.016926);
((GeneralPath)shape).curveTo(18.312786, 37.016926, 18.065374, 37.26434, 18.065374, 37.571663);
((GeneralPath)shape).curveTo(18.065374, 37.878986, 18.312788, 38.1264, 18.620111, 38.1264);
((GeneralPath)shape).lineTo(28.605383, 38.1264);
((GeneralPath)shape).curveTo(28.912706, 38.1264, 29.16012, 37.878986, 29.16012, 37.571663);
((GeneralPath)shape).curveTo(29.16012, 37.26434, 28.912706, 37.016926, 28.605383, 37.016926);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_18;
g.setTransform(defaultTransform__0_18);
g.setClip(clip__0_18);
float alpha__0_19 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_19 = g.getClip();
AffineTransform defaultTransform__0_19 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_19 is ShapeNode
paint = new Color(255, 255, 255, 245);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(29.576166, 37.606354);
((GeneralPath)shape).curveTo(29.576166, 38.276546, 30.119463, 38.819843, 30.789654, 38.819843);
((GeneralPath)shape).curveTo(31.459845, 38.819843, 32.003143, 38.276546, 32.003143, 37.606354);
((GeneralPath)shape).curveTo(32.003143, 36.936165, 31.459846, 36.392868, 30.789656, 36.392868);
((GeneralPath)shape).curveTo(30.119465, 36.392868, 29.576168, 36.936165, 29.576168, 37.606354);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_19;
g.setTransform(defaultTransform__0_19);
g.setClip(clip__0_19);
float alpha__0_20 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_20 = g.getClip();
AffineTransform defaultTransform__0_20 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_20 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.7627534866333008f, 0.0f, 0.0f, 0.7095385193824768f, 5.995510578155518f, 0.04403920844197273f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(38.417217, 15.282896);
((GeneralPath)shape).curveTo(38.417217, 15.476155, 38.417217, 15.282896, 38.417217, 15.282896);
((GeneralPath)shape).lineTo(38.016354, 15.736948);
((GeneralPath)shape).curveTo(37.77064, 15.44739, 37.494766, 15.203886, 37.214626, 14.949565);
((GeneralPath)shape).lineTo(36.59968, 15.040055);
((GeneralPath)shape).lineTo(36.037853, 14.404955);
((GeneralPath)shape).lineTo(36.037853, 15.190939);
((GeneralPath)shape).lineTo(36.5192, 15.555167);
((GeneralPath)shape).lineTo(36.83958, 15.917997);
((GeneralPath)shape).lineTo(37.26774, 15.433783);
((GeneralPath)shape).curveTo(37.37551, 15.63565, 37.481815, 15.837516, 37.588856, 16.039383);
((GeneralPath)shape).lineTo(37.588856, 16.64432);
((GeneralPath)shape).lineTo(37.106773, 17.188858);
((GeneralPath)shape).lineTo(36.224564, 17.794458);
((GeneralPath)shape).lineTo(35.55643, 18.461191);
((GeneralPath)shape).lineTo(35.128273, 17.975506);
((GeneralPath)shape).lineTo(35.342354, 17.430965);
((GeneralPath)shape).lineTo(34.91486, 16.94675);
((GeneralPath)shape).lineTo(34.19288, 15.403546);
((GeneralPath)shape).lineTo(33.577938, 14.708121);
((GeneralPath)shape).lineTo(33.416977, 14.88917);
((GeneralPath)shape).lineTo(33.658348, 15.767113);
((GeneralPath)shape).lineTo(34.1124, 16.281488);
((GeneralPath)shape).curveTo(34.371723, 17.030102, 34.628246, 17.74561, 34.96879, 18.461191);
((GeneralPath)shape).curveTo(35.49685, 18.461191, 35.99467, 18.40513, 36.519127, 18.33907);
((GeneralPath)shape).lineTo(36.519127, 18.762959);
((GeneralPath)shape).lineTo(35.877552, 20.336693);
((GeneralPath)shape).lineTo(35.289165, 21.001955);
((GeneralPath)shape).lineTo(34.80782, 22.032179);
((GeneralPath)shape).curveTo(34.80782, 22.596874, 34.80782, 23.161572, 34.80782, 23.726194);
((GeneralPath)shape).lineTo(34.968784, 24.392927);
((GeneralPath)shape).lineTo(34.70152, 24.694696);
((GeneralPath)shape).lineTo(34.1124, 25.058187);
((GeneralPath)shape).lineTo(33.497456, 25.572565);
((GeneralPath)shape).lineTo(34.006096, 26.14734);
((GeneralPath)shape).lineTo(33.310673, 26.753675);
((GeneralPath)shape).lineTo(33.44427, 27.145933);
((GeneralPath)shape).lineTo(32.401096, 28.327044);
((GeneralPath)shape).lineTo(31.706408, 28.327044);
((GeneralPath)shape).lineTo(31.118021, 28.690536);
((GeneralPath)shape).lineTo(30.742981, 28.690536);
((GeneralPath)shape).lineTo(30.742981, 28.206322);
((GeneralPath)shape).lineTo(30.583488, 27.236422);
((GeneralPath)shape).curveTo(30.376547, 26.628616, 30.16107, 26.025148, 29.941916, 25.421684);
((GeneralPath)shape).curveTo(29.941916, 24.97624, 29.968475, 24.535133, 29.995106, 24.089764);
((GeneralPath)shape).lineTo(30.263107, 23.484825);
((GeneralPath)shape).lineTo(29.888065, 22.757769);
((GeneralPath)shape).lineTo(29.915356, 21.759176);
((GeneralPath)shape).lineTo(29.406717, 21.1844);
((GeneralPath)shape).lineTo(29.661036, 20.352438);
((GeneralPath)shape).lineTo(29.247225, 19.882936);
((GeneralPath)shape).lineTo(28.524508, 19.882936);
((GeneralPath)shape).lineTo(28.28387, 19.610668);
((GeneralPath)shape).lineTo(27.56189, 20.065088);
((GeneralPath)shape).lineTo(27.268066, 19.731392);
((GeneralPath)shape).lineTo(26.5992, 20.306461);
((GeneralPath)shape).curveTo(26.145145, 19.791716, 25.69036, 19.27734, 25.235645, 18.762962);
((GeneralPath)shape).lineTo(24.70111, 17.491367);
((GeneralPath)shape).lineTo(25.182455, 16.76578);
((GeneralPath)shape).lineTo(24.915188, 16.463348);
((GeneralPath)shape).lineTo(25.502838, 15.070292);
((GeneralPath)shape).curveTo(25.985655, 14.469696, 26.489954, 13.893523, 27.000063, 13.315218);
((GeneralPath)shape).lineTo(27.909565, 13.07311);
((GeneralPath)shape).lineTo(28.925446, 12.952387);
((GeneralPath)shape).lineTo(29.620869, 13.13417);
((GeneralPath)shape).lineTo(30.610117, 14.132026);
((GeneralPath)shape).lineTo(30.957867, 13.739035);
((GeneralPath)shape).lineTo(31.438477, 13.678715);
((GeneralPath)shape).lineTo(32.347977, 13.981148);
((GeneralPath)shape).lineTo(33.0434, 13.981148);
((GeneralPath)shape).lineTo(33.524746, 13.557257);
((GeneralPath)shape).lineTo(33.738827, 13.254826);
((GeneralPath)shape).lineTo(33.256744, 12.952393);
((GeneralPath)shape).lineTo(32.45428, 12.892073);
((GeneralPath)shape).curveTo(32.2316, 12.583152, 32.02466, 12.25843, 31.76026, 11.983954);
((GeneralPath)shape).lineTo(31.492258, 12.104676);
((GeneralPath)shape).lineTo(31.385216, 12.892058);
((GeneralPath)shape).lineTo(30.90387, 12.347519);
((GeneralPath)shape).lineTo(30.797571, 11.741184);
((GeneralPath)shape).lineTo(30.263039, 11.318766);
((GeneralPath)shape).lineTo(30.048223, 11.318766);
((GeneralPath)shape).lineTo(30.58342, 11.923703);
((GeneralPath)shape).lineTo(30.36934, 12.468243);
((GeneralPath)shape).lineTo(29.941845, 12.588964);
((GeneralPath)shape).lineTo(30.209112, 12.044426);
((GeneralPath)shape).lineTo(29.727032, 11.803054);
((GeneralPath)shape).lineTo(29.300272, 11.31884);
((GeneralPath)shape).lineTo(28.497074, 11.499887);
((GeneralPath)shape).lineTo(28.390776, 11.74126);
((GeneralPath)shape).lineTo(27.90943, 12.044427);
((GeneralPath)shape).lineTo(27.642162, 12.710424);
((GeneralPath)shape).lineTo(26.974031, 13.043017);
((GeneralPath)shape).lineTo(26.679472, 12.710424);
((GeneralPath)shape).lineTo(26.35909, 12.710424);
((GeneralPath)shape).lineTo(26.35909, 11.62061);
((GeneralPath)shape).lineTo(27.054514, 11.257117);
((GeneralPath)shape).lineTo(27.589048, 11.257117);
((GeneralPath)shape).lineTo(27.481274, 10.833964);
((GeneralPath)shape).lineTo(27.054514, 10.410073);
((GeneralPath)shape).lineTo(27.775833, 10.258453);
((GeneralPath)shape).lineTo(28.176697, 9.805136);
((GeneralPath)shape).lineTo(28.497078, 9.259861);
((GeneralPath)shape).lineTo(29.086199, 9.259861);
((GeneralPath)shape).lineTo(28.925236, 8.836706);
((GeneralPath)shape).lineTo(29.300278, 8.594599);
((GeneralPath)shape).lineTo(29.300278, 9.078814);
((GeneralPath)shape).lineTo(30.102005, 9.259861);
((GeneralPath)shape).lineTo(30.903732, 8.594599);
((GeneralPath)shape).lineTo(30.957582, 8.291431);
((GeneralPath)shape).lineTo(31.65227, 7.8075852);
((GeneralPath)shape).curveTo(31.40082, 7.83885, 31.14937, 7.8618054, 30.903658, 7.9286757);
((GeneralPath)shape).lineTo(30.903658, 7.3834743);
((GeneralPath)shape).lineTo(31.170925, 6.778169);
((GeneralPath)shape).lineTo(30.903658, 6.778169);
((GeneralPath)shape).lineTo(30.316303, 7.322708);
((GeneralPath)shape).lineTo(30.15534, 7.6255083);
((GeneralPath)shape).lineTo(30.316303, 8.049766);
((GeneralPath)shape).lineTo(30.048306, 8.775352);
((GeneralPath)shape).lineTo(29.62081, 8.533244);
((GeneralPath)shape).lineTo(29.24724, 8.110089);
((GeneralPath)shape).lineTo(28.65812, 8.533244);
((GeneralPath)shape).lineTo(28.44404, 7.565183);
((GeneralPath)shape).lineTo(29.45992, 6.8995533);
((GeneralPath)shape).lineTo(29.45992, 6.5360613);
((GeneralPath)shape).lineTo(30.102156, 6.112539);
((GeneralPath)shape).lineTo(31.118034, 5.870064);
((GeneralPath)shape).lineTo(31.81346, 6.112539);
((GeneralPath)shape).lineTo(33.09653, 6.354646);
((GeneralPath)shape).lineTo(32.77615, 6.717476);
((GeneralPath)shape).lineTo(32.080727, 6.717476);
((GeneralPath)shape).lineTo(32.77615, 7.4437976);
((GeneralPath)shape).lineTo(33.310684, 6.8388605);
((GeneralPath)shape).lineTo(33.473045, 6.572697);
((GeneralPath)shape).curveTo(33.473045, 6.572697, 35.523415, 8.410387, 36.695183, 10.420592);
((GeneralPath)shape).curveTo(37.86695, 12.431458, 38.41723, 14.80155, 38.41723, 15.282896);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_20;
g.setTransform(defaultTransform__0_20);
g.setClip(clip__0_20);
float alpha__0_21 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_21 = g.getClip();
AffineTransform defaultTransform__0_21 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_21 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.7627534866333008f, 0.0f, 0.0f, 0.7095385193824768f, 5.995510578155518f, 0.04403920844197273f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(25.174515, 6.8388605);
((GeneralPath)shape).lineTo(25.12066, 7.20169);
((GeneralPath)shape).lineTo(25.495703, 7.4437976);
((GeneralPath)shape).lineTo(26.13654, 7.0202756);
((GeneralPath)shape).lineTo(25.816158, 6.657078);
((GeneralPath)shape).lineTo(25.388002, 6.899553);
((GeneralPath)shape).lineTo(25.17466, 6.8388605);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_21;
g.setTransform(defaultTransform__0_21);
g.setClip(clip__0_21);
float alpha__0_22 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_22 = g.getClip();
AffineTransform defaultTransform__0_22 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_22 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.7627534866333008f, 0.0f, 0.0f, 0.7095385193824768f, 5.995510578155518f, 0.04403920844197273f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(25.7629, 4.357463);
((GeneralPath)shape).lineTo(24.372051, 3.812556);
((GeneralPath)shape).lineTo(22.768522, 3.9939709);
((GeneralPath)shape).lineTo(20.78929, 4.5385103);
((GeneralPath)shape).lineTo(20.414984, 4.9020023);
((GeneralPath)shape).lineTo(21.644941, 5.749047);
((GeneralPath)shape).lineTo(21.644941, 6.2332616);
((GeneralPath)shape).lineTo(21.163595, 6.7174764);
((GeneralPath)shape).lineTo(21.80583, 7.989441);
((GeneralPath)shape).lineTo(22.23259, 7.7465982);
((GeneralPath)shape).lineTo(22.76852, 6.8995533);
((GeneralPath)shape).curveTo(23.594675, 6.64413, 24.335415, 6.354646, 25.120665, 5.9914484);
((GeneralPath)shape).lineTo(25.7629, 4.3573895);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_22;
g.setTransform(defaultTransform__0_22);
g.setClip(clip__0_22);
float alpha__0_23 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_23 = g.getClip();
AffineTransform defaultTransform__0_23 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_23 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.7627534866333008f, 0.0f, 0.0f, 0.7095385193824768f, 5.995510578155518f, 0.04403920844197273f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(27.206936, 9.442084);
((GeneralPath)shape).lineTo(26.992859, 8.89681);
((GeneralPath)shape).lineTo(26.617817, 9.018194);
((GeneralPath)shape).lineTo(26.72559, 9.683456);
((GeneralPath)shape).lineTo(27.206934, 9.442084);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_23;
g.setTransform(defaultTransform__0_23);
g.setClip(clip__0_23);
float alpha__0_24 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_24 = g.getClip();
AffineTransform defaultTransform__0_24 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_24 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.7627534866333008f, 0.0f, 0.0f, 0.7095385193824768f, 5.995510578155518f, 0.04403920844197273f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(27.420279, 9.319963);
((GeneralPath)shape).lineTo(27.313236, 10.047021);
((GeneralPath)shape).lineTo(27.90162, 9.925637);
((GeneralPath)shape).lineTo(28.329117, 9.502482);
((GeneralPath)shape).lineTo(27.95481, 9.13899);
((GeneralPath)shape).curveTo(27.829084, 8.804189, 27.684673, 8.491679, 27.527315, 8.170561);
((GeneralPath)shape).lineTo(27.206932, 8.170561);
((GeneralPath)shape).lineTo(27.206932, 8.533391);
((GeneralPath)shape).lineTo(27.420275, 8.775498);
((GeneralPath)shape).lineTo(27.420275, 9.320038);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_24;
g.setTransform(defaultTransform__0_24);
g.setClip(clip__0_24);
float alpha__0_25 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_25 = g.getClip();
AffineTransform defaultTransform__0_25 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_25 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.7627534866333008f, 0.0f, 0.0f, 0.7095385193824768f, 5.995510578155518f, 0.04403920844197273f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(19.506144, 20.820833);
((GeneralPath)shape).lineTo(19.077986, 19.973125);
((GeneralPath)shape).lineTo(18.27589, 19.791712);
((GeneralPath)shape).lineTo(17.8481, 18.642235);
((GeneralPath)shape).lineTo(16.778738, 18.762959);
((GeneralPath)shape).lineTo(15.869897, 18.097696);
((GeneralPath)shape).lineTo(14.906837, 18.944742);
((GeneralPath)shape).lineTo(14.906837, 19.078339);
((GeneralPath)shape).curveTo(14.615517, 18.99425, 14.257395, 18.982779, 13.998, 18.823282);
((GeneralPath)shape).lineTo(13.783922, 18.218346);
((GeneralPath)shape).lineTo(13.783922, 17.552347);
((GeneralPath)shape).lineTo(13.142348, 17.612667);
((GeneralPath)shape).curveTo(13.195898, 17.188778, 13.249024, 16.765621, 13.302943, 16.341806);
((GeneralPath)shape).lineTo(12.928269, 16.341806);
((GeneralPath)shape).lineTo(12.554332, 16.826021);
((GeneralPath)shape).lineTo(12.179658, 17.007069);
((GeneralPath)shape).lineTo(11.644756, 16.705297);
((GeneralPath)shape).lineTo(11.591207, 16.039299);
((GeneralPath)shape).lineTo(11.698249, 15.312979);
((GeneralPath)shape).lineTo(12.500711, 14.708041);
((GeneralPath)shape).lineTo(13.142285, 14.708041);
((GeneralPath)shape).lineTo(13.248961, 14.344549);
((GeneralPath)shape).lineTo(14.051057, 14.525597);
((GeneralPath)shape).lineTo(14.6394415, 15.252653);
((GeneralPath)shape).lineTo(14.746484, 14.041381);
((GeneralPath)shape).lineTo(15.762659, 13.194337);
((GeneralPath)shape).lineTo(16.136965, 12.286232);
((GeneralPath)shape).lineTo(16.885578, 11.9838);
((GeneralPath)shape).lineTo(17.313368, 11.378863);
((GeneralPath)shape).lineTo(18.275692, 11.196419);
((GeneralPath)shape).lineTo(18.757404, 10.470833);
((GeneralPath)shape).curveTo(18.27606, 10.470833, 17.794714, 10.470833, 17.313368, 10.470833);
((GeneralPath)shape).lineTo(18.222502, 10.046943);
((GeneralPath)shape).lineTo(18.863708, 10.046943);
((GeneralPath)shape).lineTo(19.773203, 9.743778);
((GeneralPath)shape).lineTo(19.880243, 9.381683);
((GeneralPath)shape).lineTo(19.559126, 9.078517);
((GeneralPath)shape).lineTo(19.184818, 8.957132);
((GeneralPath)shape).lineTo(19.291862, 8.594302);
((GeneralPath)shape).lineTo(19.024595, 8.049763);
((GeneralPath)shape).lineTo(18.382654, 8.291135);
((GeneralPath)shape).lineTo(18.489697, 7.807288);
((GeneralPath)shape).lineTo(17.741083, 7.383398);
((GeneralPath)shape).lineTo(17.153067, 8.411783);
((GeneralPath)shape).lineTo(17.206255, 8.775276);
((GeneralPath)shape).lineTo(16.618237, 9.018119);
((GeneralPath)shape).lineTo(16.243563, 9.804766);
((GeneralPath)shape).lineTo(16.083336, 9.078444);
((GeneralPath)shape).lineTo(15.067161, 8.654553);
((GeneralPath)shape).lineTo(14.906566, 8.110014);
((GeneralPath)shape).lineTo(16.243563, 7.322632);
((GeneralPath)shape).lineTo(16.831947, 6.7780924);
((GeneralPath)shape).lineTo(16.885502, 6.112463);
((GeneralPath)shape).lineTo(16.564734, 5.930682);
((GeneralPath)shape).lineTo(16.136946, 5.86999);
((GeneralPath)shape).lineTo(15.869678, 6.5359874);
((GeneralPath)shape).curveTo(15.869678, 6.5359874, 15.422467, 6.6236043, 15.307483, 6.652002);
((GeneralPath)shape).curveTo(13.839022, 8.005184, 10.871939, 10.926289, 10.182621, 16.440907);
((GeneralPath)shape).curveTo(10.209911, 16.568766, 10.682284, 17.31017, 10.682284, 17.31017);
((GeneralPath)shape).lineTo(11.805204, 17.97543);
((GeneralPath)shape).lineTo(12.928123, 18.278597);
((GeneralPath)shape).lineTo(13.409837, 18.884197);
((GeneralPath)shape).lineTo(14.158082, 19.428736);
((GeneralPath)shape).lineTo(14.585871, 19.368416);
((GeneralPath)shape).lineTo(14.90662, 19.512825);
((GeneralPath)shape).lineTo(14.90662, 19.610525);
((GeneralPath)shape).lineTo(14.479126, 20.760368);
((GeneralPath)shape).lineTo(14.158007, 21.244583);
((GeneralPath)shape).lineTo(14.265049, 21.487427);
((GeneralPath)shape).lineTo(13.997783, 22.39406);
((GeneralPath)shape).lineTo(14.960474, 24.149872);
((GeneralPath)shape).lineTo(15.922796, 24.997578);
((GeneralPath)shape).lineTo(16.350954, 25.602516);
((GeneralPath)shape).lineTo(16.297104, 26.874113);
((GeneralPath)shape).lineTo(16.618221, 27.599699);
((GeneralPath)shape).lineTo(16.297104, 28.992018);
((GeneralPath)shape).curveTo(16.297104, 28.992018, 16.271944, 28.983318, 16.312923, 29.122746);
((GeneralPath)shape).curveTo(16.354263, 29.262154, 18.02636, 30.190342, 18.132664, 30.111332);
((GeneralPath)shape).curveTo(18.238596, 30.030851, 18.329159, 29.960447, 18.329159, 29.960447);
((GeneralPath)shape).lineTo(18.222483, 29.658678);
((GeneralPath)shape).lineTo(18.649979, 29.234787);
((GeneralPath)shape).lineTo(18.810575, 28.810898);
((GeneralPath)shape).lineTo(19.505999, 28.568054);
((GeneralPath)shape).lineTo(20.040533, 27.236134);
((GeneralPath)shape).lineTo(19.880304, 26.874039);
((GeneralPath)shape).lineTo(20.253874, 26.3295);
((GeneralPath)shape).lineTo(21.05634, 26.147057);
((GeneralPath)shape).lineTo(21.484495, 25.178627);
((GeneralPath)shape).lineTo(21.377453, 23.968826);
((GeneralPath)shape).lineTo(22.019026, 23.06072);
((GeneralPath)shape).lineTo(22.126068, 22.152615);
((GeneralPath)shape).curveTo(21.248127, 21.717243, 20.377394, 21.268929, 19.505999, 20.820688);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_25;
g.setTransform(defaultTransform__0_25);
g.setClip(clip__0_25);
float alpha__0_26 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_26 = g.getClip();
AffineTransform defaultTransform__0_26 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_26 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.7627534866333008f, 0.0f, 0.0f, 0.7095385193824768f, 5.995510578155518f, 0.04403920844197273f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.329372, 7.0806003);
((GeneralPath)shape).lineTo(18.863907, 7.4437976);
((GeneralPath)shape).lineTo(19.292065, 7.4437976);
((GeneralPath)shape).lineTo(19.292065, 7.0202756);
((GeneralPath)shape).lineTo(18.75753, 6.778168);
((GeneralPath)shape).lineTo(18.329372, 7.0806003);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_26;
g.setTransform(defaultTransform__0_26);
g.setClip(clip__0_26);
float alpha__0_27 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_27 = g.getClip();
AffineTransform defaultTransform__0_27 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_27 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.7627534866333008f, 0.0f, 0.0f, 0.7095385193824768f, 5.995510578155518f, 0.04403920844197273f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.939262, 6.596753);
((GeneralPath)shape).lineTo(16.671627, 7.262383);
((GeneralPath)shape).lineTo(17.20653, 7.262383);
((GeneralPath)shape).lineTo(17.474165, 6.657078);
((GeneralPath)shape).curveTo(17.704796, 6.493981, 17.934322, 6.329854, 18.16922, 6.172863);
((GeneralPath)shape).lineTo(18.704123, 6.3546457);
((GeneralPath)shape).curveTo(19.060478, 6.596753, 19.416834, 6.83886, 19.773483, 7.0806);
((GeneralPath)shape).lineTo(20.308681, 6.596753);
((GeneralPath)shape).lineTo(19.719929, 6.354646);
((GeneralPath)shape).lineTo(19.452293, 5.809739);
((GeneralPath)shape).lineTo(18.436413, 5.6886487);
((GeneralPath)shape).lineTo(18.382856, 5.385849);
((GeneralPath)shape).lineTo(17.90151, 5.5072336);
((GeneralPath)shape).lineTo(17.687874, 5.9308295);
((GeneralPath)shape).lineTo(17.420238, 5.3859224);
((GeneralPath)shape).lineTo(17.313562, 5.62803);
((GeneralPath)shape).lineTo(17.367113, 6.233335);
((GeneralPath)shape).lineTo(16.93925, 6.5967536);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_27;
g.setTransform(defaultTransform__0_27);
g.setClip(clip__0_27);
float alpha__0_28 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_28 = g.getClip();
AffineTransform defaultTransform__0_28 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_28 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.7627534866333008f, 0.0f, 0.0f, 0.7095385193824768f, 5.995510578155518f, 0.04403920844197273f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.863909, 5.0830493);
((GeneralPath)shape).lineTo(19.131542, 4.84131);
((GeneralPath)shape).lineTo(19.666445, 4.7202196);
((GeneralPath)shape).curveTo(20.032804, 4.542042, 20.400639, 4.422055, 20.789364, 4.2963295);
((GeneralPath)shape).lineTo(20.576021, 3.9331317);
((GeneralPath)shape).lineTo(19.8856, 4.0322995);
((GeneralPath)shape).lineTo(19.559406, 4.35739);
((GeneralPath)shape).lineTo(19.021635, 4.435371);
((GeneralPath)shape).lineTo(18.543526, 4.659896);
((GeneralPath)shape).lineTo(18.31113, 4.7723055);
((GeneralPath)shape).lineTo(18.169222, 4.962328);
((GeneralPath)shape).lineTo(18.86391, 5.0830507);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_28;
g.setTransform(defaultTransform__0_28);
g.setClip(clip__0_28);
float alpha__0_29 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_29 = g.getClip();
AffineTransform defaultTransform__0_29 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_29 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.7627534866333008f, 0.0f, 0.0f, 0.7095385193824768f, 5.995510578155518f, 0.04403920844197273f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(19.77341, 10.833667);
((GeneralPath)shape).lineTo(20.094528, 10.349454);
((GeneralPath)shape).lineTo(19.612816, 9.986624);
((GeneralPath)shape).lineTo(19.77341, 10.833667);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_29;
g.setTransform(defaultTransform__0_29);
g.setClip(clip__0_29);
float alpha__0_30 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_30 = g.getClip();
AffineTransform defaultTransform__0_30 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_30 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(15.601279258728027, 12.142301559448242), 43.526714f, new Point2D.Double(15.601279258728027, 12.142301559448242), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 42)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.7488587498664856f, 0.0f, 0.0f, 0.748874306678772f, 6.056130409240723f, 0.0209099892526865f));
stroke = new BasicStroke(1.1094747f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(38.238735, 17.608438);
((GeneralPath)shape).curveTo(38.238735, 25.221281, 32.067196, 31.392754, 24.455402, 31.392754);
((GeneralPath)shape).curveTo(16.842909, 31.392754, 10.671716, 25.22121, 10.671716, 17.60844);
((GeneralPath)shape).curveTo(10.671716, 9.995948, 16.842909, 3.8251066, 24.455402, 3.8251066);
((GeneralPath)shape).curveTo(32.067196, 3.8251066, 38.238735, 9.995948, 38.238735, 17.60844);
((GeneralPath)shape).lineTo(38.238735, 17.60844);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_30;
g.setTransform(defaultTransform__0_30);
g.setClip(clip__0_30);
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
        return 2;
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
	public RemotePluginIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public RemotePluginIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public RemotePluginIcon(int width, int height) {
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

