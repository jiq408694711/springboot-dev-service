package com.chengdu.jiq.service.forkjoin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * Created by jiyiqin on 2017/11/19.
 * <p>
 * java7出现的Fork/Join框架是ExecutorService接口的一个实现，可以帮助开发人员充分利用多核处理器的优势，
 * 编写出并行执行的程序，提高应用程序的性能；设计的目的是为了处理那些可以被递归拆分的任务。
 * <p>
 * fork/join框架与其它ExecutorService的实现类相似，会给线程池中的线程分发任务，不同之处在于它使用了
 * 工作窃取算法，所谓工作窃取，指的是对那些处理完自身任务的线程，会从其它线程窃取任务执行。
 * <p>
 * fork/join框架的核心是ForkJoinPool类，用来执行被切割的任务，有两种任务：
 * 1.RecursiveTask：在线程池中运行一个本类的子类，它可以返回结果。
 * 2.RecursiveAction：和RecursiveTask类似但是不返回结果。
 * <p>
 * 在JAVA SE8中java.util.Arrays 类的parallelSort()方法。这些方法和sort()方法类似，但是可以通过fork/join框架并行执行。
 * 另一个fork/join框架的实现是在JAVA SE8中的java.util.streams包内，与Lambda表达式相关
 */
@Service
public class ForkJoinService {

    @Autowired
    private ForkJoinPool forkJoinPool;

    public Long sum(Long start, Long end) throws ExecutionException, InterruptedException {
        Future<Long> result = forkJoinPool.submit(new NumSumRecursiveTask(start, end));
        return result.get();
    }

    public void blurImage(String inputPath, String outputPath) throws IOException {
        //input
        File srcFile = new File(inputPath);
        BufferedImage image = ImageIO.read(srcFile);

        //transfer
        BufferedImage blurredImage = blur(image);

        //output
        File dstFile = new File(outputPath);
        ImageIO.write(blurredImage, "jpg", dstFile);
    }

    private BufferedImage blur(BufferedImage srcImage) {
        int w = srcImage.getWidth();
        int h = srcImage.getHeight();
        int[] src = srcImage.getRGB(0, 0, w, h, null, 0, w);
        int[] dst = new int[src.length];

        ImageBlurRecursiveAction action = new ImageBlurRecursiveAction(src, 0, src.length, dst);
        forkJoinPool.invoke(action);

        BufferedImage dstImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        dstImage.setRGB(0, 0, w, h, dst, 0, w);
        return dstImage;
    }

}
