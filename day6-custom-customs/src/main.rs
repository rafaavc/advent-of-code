fn main() {
    let mut empty_count = 0;
    let mut total_sum = 0;
    let mut answers: Vec<String> = Vec::new();
    loop {
        let mut line = String::new();
        std::io::stdin().read_line(&mut line).unwrap();

        line.pop();
        if line.is_empty() && empty_count >= 1 {
            break;
        } else if line.is_empty() {
            let mut may_be_mutual: Vec<char> = Vec::new();
            let mut not_mutual: Vec<char> = Vec::new();
            for j in answers[0].chars() {
                may_be_mutual.push(j);
            }
            for i in answers {
                for j in &may_be_mutual {
                    if !i.contains(*j) && !not_mutual.contains(j) {
                        not_mutual.push(*j);
                    }
                }
            }

            total_sum += may_be_mutual.len() - not_mutual.len();

            answers = Vec::new();
            empty_count += 1;
        } else {
            empty_count = 0;
            answers.push(line);
        }
    }
    println!("{}", total_sum);
}
