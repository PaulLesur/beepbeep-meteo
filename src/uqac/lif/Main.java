package uqac.lif;

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.FunctionProcessor;
import ca.uqac.lif.cep.input.CsvFeeder;
import ca.uqac.lif.cep.input.TokenFeeder;
import ca.uqac.lif.cep.io.LineReader;
import ca.uqac.lif.cep.numbers.IsGreaterOrEqual;
import ca.uqac.lif.cep.numbers.IsLessOrEqual;
import ca.uqac.lif.cep.tmf.Filter;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.cep.tuples.Tuple;
import ca.uqac.lif.cep.tuples.TupleFeeder;
import examples.ReadTuples;

import java.io.InputStream;

import static ca.uqac.lif.cep.Connector.LEFT;
import static ca.uqac.lif.cep.Connector.OUTPUT;
import static ca.uqac.lif.cep.Connector.RIGHT;


public class Main {

    public static void main(String[] args) throws Connector.ConnectorException {

        Fork fork = new Fork(2);
        Filter filter = new Filter();
        FunctionProcessor greaterThan = new FunctionProcessor(IsGreaterOrEqual.instance);
        TempProc tp = new TempProc();


        InputStream is = Main.class.getResourceAsStream("./temp.csv");
        QueueSource qs = new QueueSource();

        for (int i = 0; i<28; i++){
            qs.addEvent(new Integer(-40));
        }
        qs.loop(false);


        
        LineReader lr = new LineReader(is);
        TupleFeeder tf = new TupleFeeder();

        Connector.connect(lr, tf);
        Connector.connect(tf, fork);
        Connector.connect(fork, RIGHT,  tp, Connector.INPUT);
        Connector.connect(fork, LEFT, filter, LEFT);
        Connector.connect(qs, OUTPUT, greaterThan, LEFT);
        Connector.connect(tp, OUTPUT, greaterThan, RIGHT);
        Connector.connect(greaterThan, OUTPUT, filter, RIGHT);

        Pullable p = filter.getPullableOutput();

        System.out.println("RÉCAPTULATIF DES ALERTES GRAND FROID (t° < -40°C)");
        while(p.hasNext()) {
            System.out.println(p.pull().toString());
        }
    }
}
