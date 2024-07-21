
INSERT INTO "roles" ("name") VALUES ('ADMIN'), ('USER'), ('CLEANER');

INSERT INTO "users" ("user_name", "first_name", "last_name", "email", "password") VALUES ('doejohn11', 'John', 'Doe', 'johndoe@example.com', 'password'), ('smithjane234', 'Jane', 'Smith', 'janesmith@example.com', 'password'), ('sasaaki59', '輝', '笹原', 'sasahara.akira@example.com', 'password');

INSERT INTO "vendors" ("name", "zip_code", "prefecture", "city", "address", "telephone_no", "email", "representative") VALUES ('株式会社商品物産', '603-8146', '京都府', '京都市', '北区新御霊口町285', '075-231-6272', 'retail@shohinbussan.co.jp', '中西 一二三'), ('ナカタ飲料株式会社', '604-0924', '京都府', '京都市', '中京区一之船入町537-17', '075-252-5050', 'order@nakata-inryou.co.jp', '堂本 弘文');

INSERT INTO "inventory_items" ("name", "price", "stock", "vendor_id") VALUES ('歯ブラシ（青）', 124, 222, 1), ('歯ブラシ（黒）', 124, 352, 1), ('歯ブラシ（茶）', 124, 59, 1), ('緑茶（玉露）', 250, 120, 2), ('コーヒー（パック）', 75, 800, 2), ('ミネラルウォータ', 50, 1239, 2), ('シャンプー（50ml ボトル）', 180, 487, 1);
