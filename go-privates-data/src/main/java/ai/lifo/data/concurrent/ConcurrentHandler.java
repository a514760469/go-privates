package ai.lifo.data.concurrent;

/**
 * 
 * <br>
 * 类 名: ConcurrentHandler <br>
 * 描 述: 并行处理器 <br>
 * 作 者: jlz <br>
 * 创 建： 2017年2月16日 <br>
 * 版 本：v1.0.0 <br>
 * <br>
 * 历 史: (版本) 作者 时间 注释
 *
 * @author zhanglifeng
 */
public interface ConcurrentHandler {

	/**
	 * 
	 * <br>
	 * 描 述：并行处理 <br>
	 * 作 者：hanyouhui <br>
	 * 历 史: (版本) 作者 时间 注释
	 */
	void run() throws Exception;

}
