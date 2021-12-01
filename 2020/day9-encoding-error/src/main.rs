
fn pair_adds_up_to(numbers: &Vec<i64>, number: i64) -> bool {
    if numbers.len() >= 25 {
        for i in &numbers[(numbers.len()-25)..(numbers.len())] {
            for j in &numbers[(numbers.len()-25)..(numbers.len())] {
                if i != j && i+j == number {
                    return true;
                }
            }
        }
    } else {
        return true;
    }
    false
}

fn find_contiguous_sum(numbers: &Vec<i64>, number: i64) {
    for idx in 0..numbers.len() {
        let i = numbers[idx];

        let mut contiguous: Vec<i64> = Vec::new();
        contiguous.push(i);

        let mut sum = i;

        for j in &numbers[(idx+1)..numbers.len()] {
            sum += *j;
            contiguous.push(*j);

            if sum == number {
                println!("Contiguous: {:?}, sum min max: {}", contiguous, contiguous.iter().max().unwrap() + contiguous.iter().min().unwrap());
                return;
            } else if sum > number {
                break;
            }
        }
    }
}

fn main() {
    let mut numbers: Vec<i64> = Vec::new();
    let mut res: i64 = -1;
    loop{
        let mut line = String::new();
        std::io::stdin().read_line(&mut line).unwrap();
        line.pop();
        if line.is_empty() {
            break;
        } else {
            let current_num : i64 = line.parse::<i64>().unwrap();
            if !pair_adds_up_to(&numbers, current_num) {
                if res == -1 {
                    res = current_num;
                }
            }
            numbers.push(current_num);
        }

    }
    find_contiguous_sum(&numbers, res);
}
