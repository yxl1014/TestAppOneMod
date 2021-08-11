package yxl.DataHandle.Listenkafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import yxl.DataHandle.data.DirData;
import yxl.UserAndTask.entity.T_result;
import yxl.UserAndTask.util.GsonUtil;
import yxl.UserAndTask.util.LogUtil;

@Component
public class KafKaConsumer {

    @Autowired
    private DirData data;

    @KafkaListener(topics = {"topic1"})
    public void onMessage1(ConsumerRecord<?, ?> record) {
// 消费的哪个topic、partition的消息,打印出消息内容
        data.doData(GsonUtil.fromJson(record.value().toString(), T_result.class));
        LogUtil.info("简单消费：" + record.topic() + "-" + record.partition() + "-" + record.value());
    }

    @KafkaListener(topics = {"topic2"})
    public void onMessage2(ConsumerRecords<?, ?> records) {
// 消费的哪个topic、partition的消息,打印出消息内容
        for (ConsumerRecord record : records) {
            data.doData(GsonUtil.fromJson(record.value().toString(), T_result.class));
            LogUtil.info("简单消费：" + record.topic() + "-" + record.partition() + "-" + record.value());
        }
    }
}
