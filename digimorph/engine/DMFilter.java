package digimorph.engine;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.UnitSource;
import com.jsyn.unitgen.FilterStateVariable;
// import com.jsyn.unitgen.Add;
import com.jsyn.unitgen.MultiplyAdd;

public class DMFilter extends Circuit implements UnitSource {
	
	FilterStateVariable filter1;
	MultiplyAdd multiply_add1;
	
	public UnitInputPort resonance;
	public UnitInputPort frequency;
	public UnitInputPort input;
	public UnitInputPort fcv;
	public UnitInputPort fcv_amp;
	public UnitInputPort amplitude;
	public UnitOutputPort output;
	
	public DMFilter() {
		add(filter1 = new FilterStateVariable());
		// add(add1 = new Add());
		add(multiply_add1 = new MultiplyAdd());
		
		multiply_add1.output.connect(filter1.frequency);
		multiply_add1.inputA.set(1); // inputA will be FCV signal in
		multiply_add1.inputB.set(1); // input B will be FCV amplification
		multiply_add1.inputC.set(1000); // inputC is the base frequency, the Big Knob
		
		// Expose internal ports to circuit
		addPort(resonance = filter1.resonance);
		addPort(frequency = multiply_add1.inputC); // filter1.frequency); // temporary
		addPort(fcv = multiply_add1.inputA);
		addPort(fcv_amp = multiply_add1.inputB);
		addPort(input = filter1.input);
		addPort(output = filter1.output);
		addPort(amplitude = filter1.amplitude);
	}
	
	public UnitOutputPort getOutput() {
		return output;
	}

}
