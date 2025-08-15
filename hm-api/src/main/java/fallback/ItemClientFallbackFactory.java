package fallback;

import com.hmall.common.utils.CollUtils;
import com.hmapi.client.ItemClient;
import com.hmapi.dto.ItemDTO;
import com.hmapi.dto.OrderDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.Collection;
import java.util.List;

@Slf4j
public class ItemClientFallbackFactory implements FallbackFactory<ItemClient> {
    @Override
    public ItemClient create(Throwable cause) {
        return new ItemClient() {
            @Override
            public List<ItemDTO> queryByItemId(Collection<Long> ids) {
                log.info("查询商品失败！",cause);
                return CollUtils.emptyList();
            }

            @Override
            public void deductStock(List<OrderDetailDTO> items) {
                log.info("扣减商品异常",cause);
                throw new RuntimeException(cause);
            }
        };
    }
}
