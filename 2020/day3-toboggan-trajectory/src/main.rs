fn main() {
    let mut i: usize;
    let mut line_count: usize = 0;

    let mut v = vec![
        (1, 1, 0),
        (3, 1, 0),
        (5, 1, 0),
        (7, 1, 0),
        (1, 2, 0)
    ];

    loop {
        let mut line = String::new();
        std::io::stdin().read_line(&mut line).unwrap();

        line.pop();
        if line.is_empty() {
            break;
        }

        i = line.chars().count();

        for slope in &mut v {
            if line_count % slope.1 != 0 {
                continue;
            }
            let position = ((slope.0)*(line_count/slope.1)) % i;

            if line.chars().nth(position).unwrap() == '#' {
                (slope.2) += 1;
            }
        }

        line_count += 1;
    }
    let mut res = 1;
    for slope in &v {
        res *= slope.2;
        println!("{:?}", slope);
    }
    println!("{}", res);
}
