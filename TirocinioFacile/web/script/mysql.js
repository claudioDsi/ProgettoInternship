

    var con = mysql.createConnection({
      host: "localhost",
      user: "Internship",
      password: "internship",
      database: "internship"
    });

    con.connect(function(err) {
      if (err) throw err;
      con.query("SELECT * FROM Azienda", function (err, result, fields) {
        if (err) throw err;
        console.log(result);
      });
    });