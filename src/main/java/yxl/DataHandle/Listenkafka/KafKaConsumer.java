package yxl.DataHandle.Listenkafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import yxl.DataHandle.data.DirData;
import yxl.UserAndTask.entity.T_result;
import yxl.utils.GsonUtil;

import java.util.List;
import java.util.Optional;

@Component
public class KafKaConsumer {

    @Autowired
    private DirData data;

    @KafkaListener(topics = {"topic1"})
    public void onMessage1(List<ConsumerRecord<?, ?>> list) {
// 消费的哪个topic、partition的消息,打印出消息内容
        for (ConsumerRecord<?, ?> record : list) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            if (kafkaMessage.isPresent()) {
                data.doData(GsonUtil.fromJson(record.value().toString(), T_result.class));
                //LogUtil.info("简单消费：" + record.topic() + "-" + record.partition() + "-" + record.value());
            }
        }

    }

    @KafkaListener(topics = {"topic2"})
    public void onMessage2(ConsumerRecords<?, ?> records) {
// 消费的哪个topic、partition的消息,打印出消息内容
        for (ConsumerRecord record : records) {
            data.doData(GsonUtil.fromJson(record.value().toString(), T_result.class));
            //LogUtil.info("简单消费：" + record.topic() + "-" + record.partition() + "-" + record.value());
        }
    }

    @Bean
    public NewTopic topic() {
        return new NewTopic("topic1", 1, (short) 1);
    }

    @Bean
    public NewTopic dlt() {
        return new NewTopic("topic2", 1, (short) 1);
    }


}
