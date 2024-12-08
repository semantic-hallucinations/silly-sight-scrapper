import subprocess
import sys

def run_docker_compose(dir_path):
    try:

        process = subprocess.Popen(
            ["docker-compose", "up", "-d", "--build"],
            cwd=dir_path,            
            stdout=subprocess.PIPE,   
            stderr=subprocess.PIPE,   
            text=True                 
        )
        
        for line in process.stdout:
            print(line, end="")  

        for line in process.stderr:
            print(line, end="", file=sys.stderr)  

        process.wait()
        
        if process.returncode == 0:
            print("Docker Compose exited with code 0")
        else:
            print("Docker Compose exited with error")

    except Exception as e:
        print("Error: ", e)

run_docker_compose("sight_scrapper_env")

