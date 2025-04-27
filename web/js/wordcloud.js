// // 假设数据存储在一个名为data.txt的文件中，与该js文件在同一目录下
// // 首先读取数据文件
// d3.text("first20.txt", function(error, text) {
//     if (error) {
//         console.error("Error reading file:", error);
//         return;
//     }
//
//     // 将文本按行分割，然后再按空格分割每个单词及其出现次数
//     var data = text.split('\n').map(function(line) {
//         var parts = line.split(' ');
//         return {text: parts[0], size: parseInt(parts[1])};
//     });
//
//     // 设置词云图的尺寸
//     var width = 800;
//     var height = 400;
//
//     // 创建一个SVG元素来容纳词云图
//     var svg = d3.select("body").append("svg")
//         .attr("width", width)
//         .attr("height", height);
//
//     // 使用d3-cloud库创建词云布局
//     var layout = d3.layout.cloud()
//         .size([width, height])
//         .words(data)
//         .padding(5)
//         .rotate(function() { return ~~(Math.random() * 2) * 90; })
//         .font("Impact")
//         .fontSize(function(d) { return d.size; })
//         .on("end", draw);
//
//     layout.start();
//
//     function draw(words) {
//         var cloud = svg.selectAll("text")
//             .data(words)
//             .enter().append("text")
//             .style("font-family", "Impact")
//             .style("fill", function(d) { return "black"; })
//             .attr("text-anchor", "middle")
//             .attr("transform", function(d) {
//                 return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
//             })
//             .text(function(d) { return d.text; });
//     }
// });