const excelNode = require('excel4node');
const fs = require('fs');

const alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().split('');

const PATH = './res/res.csv'; // '../' si on le lance via npm start et './' si on le lance via le process java

init();

function init() {
    const csv = fs.readFileSync(PATH).toString().replace(/ /g, '');

    const json = [];
    const splitLines = csv.split('\n');
    splitLines.forEach(line => json.push(line.split(',')));

    const wb = new excelNode.Workbook();
    const ws = wb.addWorksheet('Résultat');

    let maxLine = json.length;
    let maxSubLine = 0;

    for (let indexLine = 0; indexLine < json.length; indexLine++) {
        if (json[indexLine].length > maxSubLine) maxSubLine = json[indexLine].length;

        for (let indexSubLine = 0; indexSubLine < json[indexLine].length; indexSubLine++) {
            let value = json[indexLine][indexSubLine];
            if (!value) continue;

            const style = indexLine === 0 || indexSubLine === 0 ? getStyle(wb, 14, true, false) : getStyle(wb, 12, false, false);

            ws.cell(indexLine + 1, indexSubLine + 1).number(parseFloat(value)).style(style);
        }
    }

    maxLine++;
    maxSubLine++;

    for (let i = 1; i < maxSubLine - 1; i++) {
        let letter = alphabet[i % alphabet.length];

        ws.cell(maxLine, i + 1).formula(`AVERAGE(${letter}2:${letter}${maxLine-1})`).style(getStyle(wb, 12, false, true));
    }

    for (let i = 2; i < maxLine; i++) {
        let startLetter = alphabet[1];
        let endLetter = alphabet[(maxSubLine - 2) % alphabet.length];

        ws.cell(i, maxSubLine).formula(`AVERAGE(${startLetter}${i}:${endLetter}${i})`).style(getStyle(wb, 12, false, true));
    }

    try {
        fs.mkdirSync("./res/");
    } catch {}

    wb.write('res/res.xlsx');
}

function getStyle(wb, size, bold, italics) {
    return wb.createStyle({
        font: {
            bold: bold,
            italics: italics,
            size: size,
        }
    });
}