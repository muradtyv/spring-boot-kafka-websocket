import { useState } from 'react';
import { Button, TextField, Box } from '@mui/material';
import PropTypes from "prop-types";

function UsernamePage({ setUsername }) {

    UsernamePage.propTypes = {
        setUsername: PropTypes.func.isRequired,
    };

    const [inputUsername, setInputUsername] = useState('');

    const handleUsernameSubmit = (event) => {
        console.log(inputUsername)
        event.preventDefault();
        if (inputUsername) {
            setUsername(inputUsername);
        }
    };

    return(
        <div>
            <Box>
                <h1>Type your username</h1>
                <form onClick={handleUsernameSubmit}>
                    <Box>
                        <TextField
                            sx={{
                                color: 'white', '& .MuiOutlinedInput-notchedOutline': { borderColor: 'gray' },
                                width: '300px',
                                '& .MuiOutlinedInput-root': {
                                    borderRadius: '36px',
                                    '& fieldset': {
                                        borderColor: 'gray',
                                    },
                                    '& input': {
                                        height: '8px',
                                    },
                                },
                            }}
                            inputProps={{ style: { color: 'white' } }}
                            variant="outlined"
                            placeholder="Username"
                            value={inputUsername}
                            onChange={(e) => setInputUsername(e.target.value)}
                        />
                        <Box marginLeft={2}>
                            <Button
                                variant="contained"
                                sx={{
                                    width: '94px',
                                    height: '42px',
                                    borderRadius: '36px',
                                }}
                                color="primary"
                                type="submit">
                                Enter
                            </Button>
                        </Box>
                    </Box>

                </form>
            </Box>
        </div>
    )

}
export default UsernamePage;