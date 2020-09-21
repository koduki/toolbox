/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function () {
    var simplemde = new SimpleMDE({element: document.getElementById("details")});
    simplemde.codemirror.on("change", function () {
        console.log(simplemde.value());
    });
})