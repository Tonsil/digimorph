package digimorph.engine;

import javax.swing.JApplet;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.swing.JAppletFrame;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillatorBL;

public class TestSequence extends JApplet {
	
	private static final long serialVersionUID = -2704222221111608377L;
	private Synthesizer synth;
	SawtoothOscillatorBL saw1;
	LineOut lineOut;
	
	public void init() {
		synth = JSyn.createSynthesizer();
		// Add a tone generator.
		synth.add(saw1 = new SawtoothOscillatorBL());
		
		// Add an output mixer.
		synth.add( lineOut = new LineOut() );
		
		saw1.output.connect( 0, lineOut.input, 0 );
		saw1.output.connect( 0, lineOut.input, 1 );
		
		saw1.amplitude.setup( 0.0, 1.0, 10.0 );
		saw1.frequency.set(309);

	    playScale();
	}
	
	public void start() {
		// Start synthesizer using default stereo output at 44100 Hz.
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
		TestSequence applet = new TestSequence();
		JAppletFrame frame = new JAppletFrame( "Testing", applet );
		frame.setSize( 640, 400 );
		frame.setVisible( true );
		frame.test();
	}
	
	public void playScale() {
		// From : http://www.phy.mtu.edu/~suits/notefreqs.html
		//		C4 	261.63 	132.
		//		C#4/Db4 	277.18 	124.
		//		D4 	293.66 	117.
		//		D#4/Eb4 	311.13 	111.
		//		E4 	329.63 	105.
		//		F4 	349.23 	98.8
		//		F#4/Gb4 	369.99 	93.2
		//		G4 	392.00 	88.0
		//		G#4/Ab4 	415.30 	83.1
		//		A4 	440.00 	78.4
		//		A#4/Bb4 	466.16 	74.0
		//		B4 	493.88 	69.9
		double step_time = 0.5;
		double now_time = synth.getCurrentTime() + 3;
		saw1.frequency.set(262.1, now_time);
		now_time += step_time;
		
		saw1.frequency.set(294.0, now_time);
		now_time += step_time;

		saw1.frequency.set(330.0, now_time);
		now_time += step_time;

		saw1.frequency.set(349.0, now_time);
		now_time += step_time;

		saw1.frequency.set(392.0, now_time);
		now_time += step_time;

		saw1.frequency.set(440.0, now_time);
		now_time += step_time;

		saw1.frequency.set(493.0, now_time);
		now_time += step_time;

		saw1.frequency.set(523.0, now_time);
		now_time += step_time;
	}
}
