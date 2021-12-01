

fn has_no_inf_loop(instructions: &mut Vec<(String, i32, u32)>) -> Option<i32> {
    let mut idx: i32 = 0;
    let mut acc: i32 = 0;
    loop {
        if idx as usize == instructions.len() {
            return Some(acc);
        }
        let mut instruction = &mut instructions[idx as usize];

        if instruction.2 == 1 {  // inf loop
            return None;
        }
        instruction.2 += 1;
        if instruction.0 == "acc" {
            acc += instruction.1;
        } else if instruction.0 == "jmp" {
            idx += instruction.1;
            continue;
        }
        idx += 1;
    }
}

fn clean(instructions: &mut Vec<(String, i32, u32)>) {
    for i in instructions {
        i.2 = 0;
    }
}

fn toggle_instruction(instruction: &mut (String, i32, u32)) {
    if instruction.0 == "jmp" {
        instruction.0 = String::from("nop");
    } else if instruction.0 == "nop" {
        instruction.0 = String::from("jmp");
    }
}

fn main() {
    
    let mut instructions: Vec<(String, i32, u32)> = Vec::new();
    loop{
        let mut line = String::new();
        std::io::stdin().read_line(&mut line).unwrap();
        line.pop();
        if line.is_empty() {
            break;
        } else {
            instructions.push(((&line[0..3]).to_string(), (&line[4..line.len()]).to_string().parse::<i32>().unwrap(), 0));
        }
    }
    let mut idx : u32 = 0;
    loop {
        if idx as usize == instructions.len() {
            println!("No solution found :(");
            break;
        }
        let instruction = &mut instructions[idx as usize];
        toggle_instruction(instruction);

        match has_no_inf_loop(&mut instructions) {
            None => {},
            Some(n) => {
                println!("{}", n);
                return;
            }
        }

        clean(&mut instructions);

        let instruction = &mut instructions[idx as usize];
        toggle_instruction(instruction);

        idx += 1;
    }
    
}
