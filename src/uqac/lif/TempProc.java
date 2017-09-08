package uqac.lif;

import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.ProcessorException;
import ca.uqac.lif.cep.SingleProcessor;
import ca.uqac.lif.cep.tuples.Tuple;

import java.util.Queue;
import java.util.Vector;

public class TempProc extends SingleProcessor
{


    public TempProc()
    {
        super(1, 1);
    }

    @Override
    protected boolean compute(Object[] inputs, Queue<Object[]> outputs)
    {

        Tuple temp = (Tuple)inputs[0];
        int t = Integer.parseInt(temp.get("TEMPERATURE").toString());

        Object[] front = new Object[1];
        front[0] = t;

        outputs.add(front);

        // That's it
        return true;
    }

    @Override
    public Processor clone()
    {
        return new TempProc();
    }
}