"""
Compare the output of the current Securify with past outputs.

Author: Quentin Hibon

Copyright 2018 ChainSecurity AG

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

"""
import json
import subprocess
import tempfile

from pathlib import Path

import psutil


class OutputMismatchException(Exception):
    """Raised when there is a mismatch between past and current output
    """
    pass


def test_securify_analysis(c_file, memory=4):
    """Compare the output of Securify on c_file with its expected output
    """
    mem_available = psutil.virtual_memory().available // 1024 ** 3
    assert mem_available >= memory, f'Not enough memory to run: {mem_available}G'

    with tempfile.TemporaryDirectory() as tmpdir:
        output = Path(tmpdir) / 'sec_output.json'

        cmd = ['java',
               f'-Xmx{memory}G',
               '-jar', 'build/libs/securify-0.1.jar',
               '-fs', c_file,
               '-o', output]

        try:
            print('Running:')
            print(' '.join(str(o) for o in cmd))
            subprocess.check_output(cmd, stderr=subprocess.STDOUT)
        except subprocess.CalledProcessError as exn:
            print('Securify already failed during execution!')
            raise exn

        with open(output) as fsc, open(json_output) as fsj:
            current_output = json.load(fsc)
            expected_output = json.load(fsj)
            if current_output != expected_output:
                print('Different output!')
                print('Current output:')
                print(current_output)
                print('Expected output:')
                print(expected_output)
                raise OutputMismatchException


if __name__ == '__main__':
    TESTS = Path('src/test/resources/solidity/end_to_end_testing_quick')
    for contract_file in TESTS.rglob('*.sol'):
        print(f'Running on {contract_file}')
        json_output = contract_file.with_suffix('.json')
        assert json_output.exists(), f'Missing f{json_output}'
        test_securify_analysis(contract_file)
    print('Done.')
