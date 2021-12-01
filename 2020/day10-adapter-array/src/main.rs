use std::collections::HashMap;

fn count(current_num: u64, max: u64, nums: &Vec<u64>, nums_counts: &mut HashMap<u64, u64>) -> u64 {
    if nums_counts.contains_key(&current_num) {
        return *nums_counts.get(&current_num).unwrap();
    }

    let mut res = 0;
    for i in 1..4 {
        let current_value = current_num + i;
        if nums.contains(&current_value) {
            res += count(current_value, max, nums, nums_counts);
        }
        if current_value == max {
            res += 1;
        }
    }

    nums_counts.insert(current_num, res);

    return res;
}

fn main() {
    let mut nums_counts: HashMap<u64, u64> = HashMap::new();
    let mut nums: Vec<u64> = Vec::new();
    loop{
        let mut line = String::new();
        std::io::stdin().read_line(&mut line).unwrap();
        line.pop();
        if line.is_empty() {
            break;
        } else {
            let current_num : u64 = line.parse::<u64>().unwrap();
            nums.push(current_num);
        }
    }

    let max = *nums.iter().max().unwrap();

    let c = count(0, max, &nums, &mut nums_counts);

    println!("RES = {}", c);
}
