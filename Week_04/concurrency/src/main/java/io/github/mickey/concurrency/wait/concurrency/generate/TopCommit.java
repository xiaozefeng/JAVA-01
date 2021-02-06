package io.github.mickey.concurrency.wait.concurrency.generate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
  #!/bin/zsh

  for file in `ls`
  do

 	if [ -d $file ] ; then
  		#cd  $file && git pull origin "$(git branch --show-current)" && cd ..
  		#cd $file && find . -type f | egrep "\.(js|html|css|vue|json)" | xargs wc -l > total.txt && cd ..
  cd $file  && git log --format='%aN' | sort -u | while read name; do echo -en "$name\t"; git log --author="$name" --pretty=tformat: --numstat | awk '{ add += $1; subs += $2; loc += $1 - $2 } END{ print loc }' ; done > commit.txt && cd ..
  	fi
  done
 */
public class TopCommit {

    private static Map<String, Integer> m = new HashMap<>();

    public static void main(String[] args) throws IOException {


        String origin = "./otmgroup-eureka-server/commit.txt\n" +
                "./hotel-operation-platform-points/commit.txt\n" +
                "./otmgroup-distribution/commit.txt\n" +
                "./plumelog/commit.txt\n" +
                "./otmgroup-wechat-ability/commit.txt\n" +
                "./otmgroup-gateway/commit.txt\n" +
                "./hotel-operation-platform-auth/commit.txt\n" +
                "./hotel-operation-platform-admin/commit.txt\n" +
                "./hotel-operation-platform-booking/commit.txt\n" +
                "./hotel-property/commit.txt\n" +
                "./pms-hotel-data-sync/commit.txt\n" +
                "./hotel-operation-platform-balance/commit.txt\n" +
                "./hotel-operation-platform-message/commit.txt\n" +
                "./hotel-operation-platform-goods/commit.txt\n" +
                "./hotel-operation-platform-ability/commit.txt\n" +
                "./hotel-operation-platform-coupon/commit.txt\n" +
                "./otmgroup-data-processing-ability/commit.txt\n" +
                "./hotel-operation-platform-server/commit.txt\n" +
                "./hotel-operation-platform-pay-center/commit.txt\n" +
                "./otmgroup-deposit/commit.txt\n" +
                "./hotel-operation-platform-merchant/commit.txt\n" +
                "./hotel-operation-platform-member/commit.txt";


        String unionbuy = "./unionbuy-member-lifeng/commit.txt\n" +
                "./unionbuy-good-lifeng/commit.txt\n" +
                "./unionbuy-order/commit.txt\n" +
                "./unionbuy-admin/commit.txt\n" +
                "./unionbuy-server/commit.txt\n" +
                "./unionbuy-member/commit.txt\n" +
                "./unionbuy-goods/commit.txt";

        String micmall = "./otmgroup-mall-admin/commit.txt\n" +
                "./otmgroup-mall-server/commit.txt\n" +
                "./otmgroup-mall-booking/commit.txt\n" +
                "./otmgroup-mall-product/commit.txt";

        String frontEnd = "./hotel_cms/commit.txt\n" +
                "./otm-mini-mall/commit.txt\n" +
                "./xiaodian2s/commit.txt\n" +
                "./promoters/commit.txt\n" +
                "./mp-merchant-booking/commit.txt\n" +
                "./fe-code-spec/commit.txt\n" +
                "./official-website/commit.txt\n" +
                "./legao/commit.txt\n" +
                "./legao-socket/commit.txt\n" +
                "./legao-material/commit.txt\n" +
                "./hotel-cms-wxapp/commit.txt\n" +
                "./hotel_operate_cms/commit.txt\n" +
                "./legao-jimu/commit.txt\n" +
                "./document/commit.txt\n" +
                "./wineshop/commit.txt\n" +
                "./unionpay-lifeng/commit.txt\n" +
                "./unionpay_cms/commit.txt\n" +
                "./unionpay/commit.txt";

        List<String> paths = processPaths("/Users/mickey/hop", origin);
        paths.addAll(processPaths("/Users/mickey/unionbuy", unionbuy));
        paths.addAll(processPaths("/Users/mickey/micro-mall", micmall));
        paths.addAll(processPaths("/Users/mickey/zsl/front-end", frontEnd));

        for (String path : paths) {
            String content = readFileToString(path);
            processContent(m, content);
        }

        removeCommit(m);
        mergeCommit(m);
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> o2.count.compareTo(o1.count));
        m.forEach((k, v) -> {
            pq.add(new Node(v, k));
        });


        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }

    }

    private static void mergeCommit(Map<String, Integer> m) {
        Map<String, String[]> combines = new HashMap<>();
        combines.put("新任", new String[]{"yexinren", "swimYe", "xinren.ye", "叶新任"});
        combines.put("子华", new String[]{"zihua.feng", "zihua", "'zihua'", "冯子华", "fengzihua"});
        combines.put("米奇", new String[]{"mickey", "肖泽锋", "xiaozefeng" });
        combines.put("邵伟", new String[]{"fanshaowei"  });
        combines.put("大叔", new String[]{"KelvinChan"  , "jialiang.chen", "chenjialiang"});
        combines.put("国森", new String[]{"sam.feng"  , "冯国森"});
        combines.put("纳兹", new String[]{"natus"});
        combines.put("Laughing", new String[]{"luaghingzsm"});
        combines.put("暴龙", new String[]{"baolongge"});
        combines.put("断浪", new String[]{"duanlang"});
        combines.put("盖子", new String[]{"Gaiz", "余子煌"});
        combines.put("tom", new String[]{"huangshitang", "tom.huang"});


        combines.forEach((k,v) -> {
            int total = 0;
            for (String key : v) {
                total += m.get(key);
            }
            m.put(k, total);
        });

        combines.forEach((k,v) ->{
            for (String s : v) {
                m.remove(s);
            }
        });


    }

    private static void removeCommit(Map<String, Integer> m) {
        String[] waitToRemove = {"tim.lin",
                "yuchao",
                "Administrator",
                "gitter.zeng",
                "禅寂",
                "官德志", "luoyi", "qing.wang", "qihua.li", "winter", "yufuyong", "zehao.li",
                "jason.deng", "jason", "min",
                "fuyong.yu", "unknown", "xingwantang", "kw", "Beke", "linyf", "115068368", "laugon.liu", "a4848438@foxmail.com", "管理员", "陈宇超", "admin",
                "deng.you", "joe.cai", "JinYao", "yunqian", "jc", "xiemin", "zoryee.yu", "laiht", "谢敏", "JC", "dengyou", "zi", "zhongmei",
                "windFollower29","apple","蔡庆丰","姜云骞","邓游","@you.deng", "jialiang","yunqian.jiang"
        };
        for (String s : waitToRemove) {
            m.remove(s);
        }

    }

    private static void processContent(Map<String, Integer> m, String content) {
        String[] lines = content.split("\n");
        for (String line : lines) {
            String[] commit = line.split("\t");
            if (commit.length < 2) {
                continue;
            }
            String key = commit[0];
            m.put(key, m.getOrDefault(key, 0) + Integer.parseInt(commit[1]));
        }
    }

    private static List<String> processPaths(String prefix, String origin) {
        String[] paths = origin.split("\n");
        return Arrays.stream(paths).map(e -> prefix + e.substring(1)).collect(Collectors.toList());
    }

    private static String readFileToString(String path) throws IOException {
        // return Files.readString(Paths.get(path));
        return "";
    }

    private static class Node {
        @Override
        public String toString() {
            return name +" "+ count;
        }

        Integer count;
        String name;

        public Node(Integer count, String name) {
            this.count = count;
            this.name = name;
        }
    }
}
