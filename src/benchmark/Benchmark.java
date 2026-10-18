package benchmark;
import Utils.BlobFinder;
import Utils.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(time = 1, timeUnit = TimeUnit.SECONDS)
public class Benchmark {

    @State(Scope.Benchmark)
    public static class Data {

//        BufferedImage image;
//        BlobFinder finder;
        int FrameCount;
        @Setup(Level.Iteration)
        public void GenerateImage(BenchmarkParams params) {
//            image = Benchmark.GenerateImage(Integer.parseInt(params.getParam("elementCount")));
//            finder= Benchmark.CreateBlobFinder(Integer.parseInt(params.getParam("elementCount")));
            FrameCount =Integer.parseInt(params.getParam("elementCount"));
        }


    }

    @Param({"10", "50", "100", "150","200"})
    public int elementCount;
    @org.openjdk.jmh.annotations.Benchmark
    public void testCode(Data data) {
      Main.main(new String[]{Integer.toString(data.FrameCount)});
   }

//    static BufferedImage GenerateImage(int count) {
//        double cnt= Math.sqrt(count);
//        BufferedImage img =new BufferedImage((int)cnt, (int)cnt, BufferedImage.TYPE_INT_RGB);
//        return img;
//    }
//    static BlobFinder CreateBlobFinder(int count) {
//        double cnt= Math.sqrt(count);
//        return new BlobFinder((int)cnt,(int)cnt,180);
//    }
//
//    @org.openjdk.jmh.annotations.Benchmark
//    public void testBlobFinder(Data data) {
//       data.finder.FindBlobs(data.image,10);
//    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Benchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
