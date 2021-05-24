/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function alertFuction() {
    let x;
    let y;
    for (var i = 1; i < 31; i++) {
        for (var e = 1; e < 31; e++) {
            if (e - 2 == (i + 2) / 2 && i - 2 == e + 2) {
                x = i;
                y = e;
            }
        }
    }

    return("Schäfer Sepp hat " + x + " Schafe, Schäfer Anton " + y + " Schafe");
}

function multiplication() {
    let x = document.getElementById("z1").value;
    let y = document.getElementById("z2").value;
    let sol = 0 + y * 2 / 2;
    while (x > 1) {
        x = Math.trunc(x / 2);
        y = y * 2;

        if (x / 2 != Math.floor(x / 2)) {
            sol = sol + y;
        }
    }
    return sol;
}

function kehrwertaddition() {
    let x = document.getElementById("z3").value;
    let sum = 0;

    while (x != 0) {
        let y = x % 10;
        x = Math.floor(x / 10);

        sum = sum + (1 / y);
    }
    return sum;
}

function decimalToHex(x) {
    let number = Number(x);
    let hex = number.toString(16);
    return (hex.toString()).toUpperCase();
}
