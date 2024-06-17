package ai.lifo.data.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * <br>
 * 类 名: SimpleTemplate<C_E> <br>
 * 描 述: 实现生产、消费（适用于生产、消费在一个类里完成且只有一个生产、消费组合，并且方法入参列表简单）简易模板 <br>
 * 作 者:  jlz <br>
 * 创 建：  2017年2月16日 <br>
 * 版 本：v1.0.0 <br>
 * <br>
 * 历 史: (版本) 作者 时间 注释
 * @author zhanglifeng
 */
public interface SimpleTemplate<E> {

	/**
	 * 
	 * <br>
	 * 描 述：生产数据 <br>
	 * 作 者：hanyouhui <br>
	 * 历 史: (版本) 作者 时间 注释
	 * 
	 * @param context context
	 * @throws Exception exception
	 */
	void production(Context<E> context) throws Exception;

	/**
	 * 
	 * <br>
	 * 描 述：消费数据 <br>
	 * 作 者：hanyouhui <br>
	 * 历 史: (版本) 作者 时间 注释
	 * 
	 * @param context context
	 * @throws Exception Exception
	 */
	default void consumption(Context<E> context) throws Exception {

		while (true) {
			E e = context.pollDataFromConsumptionQueue();
			if (e == null) {
				break;
			}
			consumption(e);
		}
	}


	void consumption(E e);
}
