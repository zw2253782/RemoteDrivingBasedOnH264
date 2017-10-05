package utility;


import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;

import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;

public class Trace implements Serializable {

    public long time;
    public double [] values = null;
    public int dim;
    public String type = "none";


    public long videoSendTime = 0;
    public long sequenceNo = 0;
    public long roundLatency = 0;
    public long oraginalSize = 0;
    public boolean isIFrame = false;
    public long PCtime = 0;
    public long comDataSize = 0;
    public long PCReceivedDataSize = 0;

    private static String TAG = "Trace";

    public static String ACCELEROMETER = "accelerometer";
    public static String GYROSCOPE = "gyroscope";
    public static String MAGNETOMETER = "magnetometer";
    public static String LATENCY = "latency";

    public static String ROTATION_MATRIX = "rotation_matrix";
    public static String GPS = "gps";


    public Trace() {

    }

    public Trace(int d) {
        time = 0;
        dim = d;
        values = new double [dim];
    }

    public void setValues(double x, double y, double z) {
        values[0] = x;
        values[1] = y;
        values[2] = z;
        dim = 3;
    }


    public void copyTrace(Trace trace) {
        this.time = trace.time;
        this.dim = trace.dim;
        this.values = new double[dim];
        for(int i = 0; i < dim; ++i) {
            this.values[i] = trace.values[i];
        }
    }

    public String toJson() {
        StringWriter sw = new StringWriter();
        JsonWriter writer = new JsonWriter(sw);

        try {
            writer.beginObject();
            writer.name("type").value(type);
            writer.name("time").value(time);
            writer.name("videoSendTime").value(videoSendTime);
            writer.name("sequenceNo").value(sequenceNo);
            writer.name("roundLatency").value(roundLatency);
            writer.name("oraginalSize").value(oraginalSize);
            writer.name("PCtime").value(PCtime);
            writer.name("comDataSize").value(comDataSize);
            writer.name("PCReceivedDataSize").value(PCReceivedDataSize);
            writer.name("isIFrame").value(isIFrame);

            for (int i = 0; i < dim; ++i) {
                writer.name("x" + String.valueOf(i)).value(values[i]);
            }
            writer.endObject();
            writer.flush();
        } catch (Exception e) {
            Log.d(TAG, "convert to json failed");
        }
        return sw.toString();
    }

    public void fromJson(String json) {
        StringReader sr = new StringReader(json);
        JsonReader reader = new JsonReader(sr);
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("type")) {
                    type = reader.nextString();
                } else if (name.equals("time")) {
                    time = reader.nextLong();
                } else if (name.equals("dim")) {
                    dim = reader.nextInt();
                    values = new double[dim];
                } else if (name.contains("x")) {
                    int index = Integer.valueOf(name.substring(1)).intValue();
                    values[index] = (double) reader.nextDouble();
                }else if (name.equals("videoSendTime")) {
                    videoSendTime = reader.nextLong();
                }else if (name.equals("sequenceNo")) {
                    sequenceNo = reader.nextLong();
                }else if (name.equals("roundLatency")) {
                    roundLatency = reader.nextLong();
                }else if (name.equals("oraginalSize")) {
                    oraginalSize = reader.nextLong();
                }else if (name.equals("PCtime")) {
                    PCtime = reader.nextLong();
                }else if (name.equals("comDataSize")) {
                    comDataSize = reader.nextLong();
                }else if (name.equals("PCReceivedDataSize")) {
                    PCReceivedDataSize = reader.nextLong();
                } else if (name.equals("isIFrame")) {
                    isIFrame = reader.nextBoolean();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        } catch (Exception e) {
            Log.d(TAG, "read to json failed");
        }
    }

}