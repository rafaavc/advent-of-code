
fn main() {
    let mut v : Vec<i32> = Vec::new();
    loop {
        let mut line = String::new();
        std::io::stdin().read_line(&mut line).unwrap();
        line.pop();
        let num = line.parse::<i32>().unwrap();
        if num == -1 {
            break;
        }
        v.push(num);
    }

    for i in &v {
        for j in &v {
            for k in &v {
                if *i + *j + *k == 2020 {
                    let mult = *i * *j * *k;
                    println!("{}", mult);
                    return;
                }
            }
        }
    }
}
