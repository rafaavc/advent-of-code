
fn calculate(s : String, mut top: i32, mut bottom: i32) -> i32 {
    for i in s.chars() {
        let dist: i32 = ((top-bottom) as f32 / 2.).floor() as i32;
        
        if i == 'F' || i == 'L' { // lower
            if dist == 0 {
                return bottom;
            }
            top = bottom + dist;
        } else if i == 'B' || i == 'R' { // upper
            if dist == 0 {
                return top;
            }
            bottom = top - dist;
        }
    }
    return -1;
}

fn main() {
    let mut v: Vec<i32> = Vec::new();

    loop {
        let mut line = String::new();
        std::io::stdin().read_line(&mut line).unwrap();

        line.pop();
        if line.is_empty() {
            break;
        }

        let row_code : String = line.chars().take(7).collect();
        let column_code : String = line.chars().skip(7).take(3).collect();

        let row: i32 = calculate(row_code, 127, 0);
        let column: i32 = calculate(column_code, 7, 0);
        
        if row == -1 || column == -1 { println!("Error calculating."); return; }
        let id = row*8 + column;
        
        if row == 0 || row == 127 { continue; } // not front nor back

        v.push(id);
    }

    for bp in &v {
        for bp1 in &v {
            if *bp == *bp1 + 2 {
                let my_bp = *bp1 +1;
                if !v.contains(&my_bp) {
                    println!("{}", my_bp); 
                    return; 
                }
            }
        }
    }
    println!("Not found :(");
}
