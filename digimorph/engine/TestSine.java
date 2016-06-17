package digimorph.engine;

import javax.swing.JApplet;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.swing.JAppletFrame;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.Add;

// import com.jsyn.unitgen.FilterLowPass;
import com.jsyn.unitgen.FilterStateVariable;

public class TestSine extends JApplet {
	
	private static final long serialVersionUID = -2704222221111608377L;
	private Synthesizer synth;
	SawtoothOscillatorBL saw_osc1;
	SawtoothOscillatorBL saw_osc2;
	TriangleOscillator lfo1;
	TriangleOscillator lfo2;
	Add add1;

	// FilterLowPass lowpass_filter1;
	FilterStateVariable lowpass_filter1;
	LineOut lineOut;
	
	public void init() {
		synth = JSyn.createSynthesizer();
		// Add a tone generator.
		synth.add( saw_osc1 = new SawtoothOscillatorBL() );
		synth.add( saw_osc2 = new SawtoothOscillatorBL() );
		
		synth.add(lfo1 = new TriangleOscillator());
		synth.add(lfo2 = new TriangleOscillator());
		synth.add(add1 = new Add());
		
		synth.add(lowpass_filter1 = new FilterStateVariable()); 

		// Add an output mixer.
		synth.add( lineOut = new LineOut() );
		
		saw_osc1.output.connect(lowpass_filter1.input);
		saw_osc2.output.connect(lowpass_filter1.input);

		lowpass_filter1.amplitude.setup( 0.0, 1.0, 10.0 );
		// lowpass_filter1.frequency.set(600);
		lowpass_filter1.resonance.set(0.5);
		// lowpass_filter1.output.connect( 0, lineOut.input, 0 );
		// lowpass_filter1.output.connect( 0, lineOut.input, 1 );
		
		saw_osc1.amplitude.setup( 0.0, 1.0, 10.0 );
		saw_osc2.amplitude.setup( 0.0, 1.0, 10.0 );
		saw_osc1.frequency.set(309);
		saw_osc2.frequency.set(262);
		lfo1.amplitude.set(1000);
		lfo1.frequency.set(30);
		lfo2.frequency.set(1000);
		// lfo1.output.connect(lowpass_filter1.frequency);
		lfo1.output.connect(add1.inputA);
		// lfo2.output.connect(add1.inputB);
		add1.output.connect( 0, lineOut.input, 0 ); // (lowpass_filter1.frequency);
	}
	
	public void start() {
		// Start synthesizer using default stereo output at 44100 Hz.
		// lfo1.start();
		synth.start();
		// We only need to start the LineOut. It will pull data from the oscillator.
		lineOut.start();
	}

	public void stop() {
		synth.stop();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestSine applet = new TestSine();
		JAppletFrame frame = new JAppletFrame( "Testing", applet );
		frame.setSize( 640, 400 );
		frame.setVisible( true );
		frame.test();
	}

}
