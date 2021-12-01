#[macro_use] extern crate text_io;

fn main() {
    let mut valid_count : u32 = 0;
    loop {
        let (lower_limit, upper_limit, char_to_count, password): (usize, usize, char, String);
        
        let mut line = String::new();
        std::io::stdin().read_line(&mut line).unwrap();

        line.pop();
        if line.is_empty() {
            break;
        }

        scan!(line.bytes() => "{}-{} {}: {}", lower_limit, upper_limit, char_to_count, password);

        /*// Part 1
        let count = password.matches(char_to_count).count() as i32;
        if lower_limit <= count && upper_limit >= count {
            valid_count += 1;
        }*/

        // Part 2
        let is_first : bool = password.chars().nth(lower_limit-1).unwrap() == char_to_count;
        let is_second : bool = password.chars().nth(upper_limit-1).unwrap() == char_to_count;

        if (is_first || is_second) && !(is_first && is_second) {
            valid_count += 1;
        }

    }
    println!("{}", valid_count);
}