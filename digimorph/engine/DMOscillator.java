package digimorph.engine;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.SquareOscillatorBL;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.UnitSource;
import com.jsyn.unitgen.Add;
import com.jsyn.unitgen.PassThrough;
// import com.jsyn.unitgen.MultiplyAdd;

public class DMOscillator extends Circuit implements UnitSource {
	
	SineOscillator sine_osc1;
	SawtoothOscillatorBL saw_osc1;
	SquareOscillatorBL square_osc1;
	TriangleOscillator triangle_osc1;
	Add add1;
	// MultiplyAdd multiply_add1;
	
	public UnitInputPort amplitude;
	public UnitInputPort frequency;
	public UnitInputPort tuning; // double value from 0 to 2 
	public UnitOutputPort output;
	public PassThrough pass_through1;
	
	public DMOscillator() {
		add(sine_osc1 = new SineOscillator());
		add(saw_osc1 = new SawtoothOscillatorBL());
		add(square_osc1 = new SquareOscillatorBL());
		add(triangle_osc1 = new TriangleOscillator());
		add(add1 = new Add());
		add(pass_through1 = new PassThrough());
		saw_osc1.output.connect(add1.inputA);
		
		// Expose internal ports to circuit
		addPort(amplitude = saw_osc1.amplitude);
//		addPort(frequency = saw_osc1.frequency);
		addPort(frequency = pass_through1.input);
		addPort(output = add1.output);
		
		pass_through1.output.connect(sine_osc1.frequency);
		pass_through1.output.connect(saw_osc1.frequency);
		pass_through1.output.connect(square_osc1.frequency);
		pass_through1.output.connect(triangle_osc1.frequency);
	}
	
	public UnitOutputPort getOutput() {
		return output;
	}
	
	public void setWaveform(String osc_name) {
		System.out.println(osc_name);
		switch (osc_name) {
			case "sine":
				sine_osc1.output.connect(add1.inputA);
				saw_osc1.output.disconnect(add1.inputA);
				square_osc1.output.disconnect(add1.inputA);
				triangle_osc1.output.disconnect(add1.inputA);
				break;
				
			case "saw":
				saw_osc1.output.connect(add1.inputA);
				sine_osc1.output.disconnect(add1.inputA);
				square_osc1.output.disconnect(add1.inputA);
				triangle_osc1.output.disconnect(add1.inputA);
				break;
				
			case "square":
				square_osc1.output.connect(add1.inputA);
				saw_osc1.output.disconnect(add1.inputA);
				sine_osc1.output.disconnect(add1.inputA);
				triangle_osc1.output.disconnect(add1.inputA);
				break;

			case "triangle":
				triangle_osc1.output.connect(add1.inputA);
				square_osc1.output.disconnect(add1.inputA);
				saw_osc1.output.disconnect(add1.inputA);
				sine_osc1.output.disconnect(add1.inputA);
				break;
		}
	}

}
