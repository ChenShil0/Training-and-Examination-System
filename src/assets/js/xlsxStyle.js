function derive (json,arr,pageName,tableName) {
    //处理null和undefined
    for (let i = 0; i <json.length;i++ ) {
        for (let j = 0; j < json[i].length;j++) {
            if (json[i][j]==null||json[i][j] == undefined) {
                json[i][j] = ''
            }
        }
    }
    const wb = XLSX.utils.book_new(); //创建一个文件
    const ws = XLSX.utils.aoa_to_sheet(json)
    //总共多少列
    // let arr = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA","AB","AC","AD","AE","AF","AG","AH"] 
    //设置公共样式
     arr.forEach((item) => {
        for (let i = 1; i < 7 + json.length; i++) {
            let str = (item + i).toString();
            if (ws[str]) {
                if (i >= 3) {
                    if (item == "B") {
                        Object.assign(ws[str], { t: "n" })
                    } else {
                        Object.assign(ws[str], { t: "s" })
                    }
                }
                ws[str].s = {
                    font: {
                        name: "宋体",
                        sz: 10,
                        bold: false,
                        color: { rgb: "000000" }
                    },
                    border: {
                        top: {
                            style: 'thin',
                            color: {
                                auto: 1
                            }
                        },
                        left: {
                            style: 'thin',
                            color: {
                                auto: 1
                            }
                        },
                        right: {
                            style: 'thin',
                            color: {
                                auto: 1
                            }
                        },
                        bottom: {
                            style: 'thin',
                            color: {
                                auto: 1
                            }
                        }
                    },
                }
            }
        }
    })
    ws["A1"].s = {
        font: {
            sz: 14,
            bold: true,
            color: { rgb: "000000" }
        },
        fill: {
            bgColor: { indexed: 64 },
            fgColor: { rgb: "ffffff" }
        },
        alignment: {
            horizontal: "center",
            vertical: "center"
        },
    };
    arr.forEach((item) => {
        for (let i = 1; i < 7 + json.length; i++) {
            let str = (item + 2).toString();
            if (ws[str]) {
                ws[str].s = {
                    font: {
                        name: "宋体",
                        sz: 10,
                        bold: true,
                        color: { rgb: "000000" }
                    },
                    border: {
                        top: {
                            style: 'thin',
                            color: {
                                auto: 1
                            }
                        },
                        left: {
                            style: 'thin',
                            color: {
                                auto: 1
                            }
                        },
                        right: {
                            style: 'thin',
                            color: {
                                auto: 1
                            }
                        },
                        bottom: {
                            style: 'thin',
                            color: {
                                auto: 1
                            }
                        }
                    },
                    fill: {
                        bgColor: { indexed: 64 },
                        fgColor: { rgb: "cccccc" }
                    },
                    alignment: {
                        horizontal: "center",
                        vertical: "center"
                    },
                }
            }
        }
    })
    
    let changdu = arr.length-1
    ws["!merges"] = [{ s: { r: 0, c: 0 }, e: { r: 0, c: changdu } }];
    let clos = []
    for (let i = 0; i <= changdu; i++) {
        clos.push({
            wpx: 120
        })
    }
    ws["!cols"] = clos
    XLSX.utils.book_append_sheet(wb, ws, pageName);
    XLSX.writeFile(wb, tableName+".xlsx");
}