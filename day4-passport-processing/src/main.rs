use std::collections::HashMap;
use regex::Regex;

fn convert_to_positive_int(my_string: String) -> i32 {
    let f = my_string.parse::<i32>();
    match f {
        Ok(f) => {
            return f;
        },
        Err(_e) => {
            return -1
        }
    }
}

fn check_interval(my_string: String, lower: i32, upper: i32) -> bool {
    let num = convert_to_positive_int(my_string);
    if num == -1 { false } else { num >= lower && num <= upper }
}

fn main() {
    let mut fields: HashMap<&str, fn(String) -> bool> = HashMap::new();
    fields.insert("byr", |value: String| -> bool {
        check_interval(value, 1920, 2002)
    });
    fields.insert("iyr", |value: String| -> bool {
        check_interval(value, 2010, 2020)
    });
    fields.insert("eyr", |value: String| -> bool {
        check_interval(value, 2020, 2030)
    });
    fields.insert("hgt", |mut value: String| -> bool {
        if value.matches("cm").count() > 0 {
            value.pop(); value.pop();
            return check_interval(value, 150, 193);
        } else if value.matches("in").count() > 0 {
            value.pop(); value.pop();
            return check_interval(value, 59, 76);
        }
        false
    });
    fields.insert("hcl", |value: String| -> bool {
        let re = Regex::new(r"^#[a-f0-9]{6}$").unwrap();
        re.is_match(&&value)
    });
    fields.insert("ecl", |value: String| -> bool {
        let ecls = ["amb", "blu", "brn", "gry", "grn", "hzl", "oth"];
        ecls.contains(&&value[..])
    });
    fields.insert("pid", |value: String| -> bool {
        value.chars().filter(|value| -> bool {
            !value.is_digit(10)
        }).count() == 0 && value.chars().count() == 9
    });

    let mut count = 0;
    let mut current_passport: String = String::new();
    let mut empty_count = 0;
    loop{
        let mut line = String::new();
        std::io::stdin().read_line(&mut line).unwrap();

        line.pop();
        if line.is_empty() && empty_count >= 1 {
            break;
        }
        else if line.is_empty() {
            
            let mut valid = true;

            for st in &fields {
                if current_passport.matches(st.0).count() == 0 {
                    valid = false;
                    break;
                } else {
                    let expr = format!(r"{}:.* ", st.0);
                    let re = Regex::new(&expr).unwrap();
                    let mut found : String = String::from(&re.captures(&current_passport).unwrap()[0]);
                    
                    while found.matches(' ').count() > 0 {
                        found.pop();
                    }
                    
                    let mut value = String::new();
                    while found.matches(':').count() > 0 {
                        let a : char = found.pop().unwrap();
                        if a == ':' { break; }
                        value.push(a);
                    }
                    value = value.chars().rev().collect::<String>();
                    if !st.1(value) {
                        valid = false;
                        break;
                    }
                }
            }
            if valid {
                count += 1;
            }
            current_passport = String::new();
            empty_count += 1;
        } else {
            empty_count = 0;
            current_passport.push_str(&line[..]);
            current_passport.push_str(" ");
        }
    }
    println!("{}", count);
}
